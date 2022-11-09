pipeline {
    agent any
    options {
        timeout(time: 1, unit: 'HOURS') // set timeout 1 hour
    }
    environment {
        TIME_ZONE = 'Asia/Seoul'
        PROFILE = 'prod'

        REPOSITORY_CREDENTIAL_ID = 'gitlab-jenkins-key'
        REPOSITORY_URL = 'git@git.swmgit.org:swm-13-main/13_swm56/belloga-labeling-verification-service.git'
        TARGET_BRANCH = 'master'

        CONTAINER_NAME = 'belloga-labeling-verification-service'

        AWS_CREDENTIAL_NAME = 'belloga-aws'
        ECR_PATH = '023778162658.dkr.ecr.ap-northeast-2.amazonaws.com'
        IMAGE_NAME = '023778162658.dkr.ecr.ap-northeast-2.amazonaws.com/belloga-labeling-verification-service'
        REGION = 'ap-northeast-2'
    }
    stages{
        stage('init') {
            steps {
                echo 'init stage'
                deleteDir()
            }
            post {
                success {
                    echo 'success init in pipeline'
                }
                failure {
                    error 'fail init in pipeline'
                }
            }
        }
        stage('clone project') {
            steps {
                git url: "$REPOSITORY_URL",
                    branch: "$TARGET_BRANCH",
                    credentialsId: "$REPOSITORY_CREDENTIAL_ID"
                sh "ls -al"
            }
            post {
                success {
                    echo 'success clone project'
                }
                failure {
                    error 'fail clone project' // exit pipeline
                }
            }
        }
        stage('create application-prod') {
            steps {
                dir('src/main/resources') {
				withAWSParameterStore(credentialsId: "${env.AWS_CREDENTIAL_NAME}",
              				path: "/belloga/labeling-verification/application/profile",
              				naming: 'basename',
              				regionName: 'ap-northeast-2') {

                        writeFile file: 'application-prod.yml', text: "${env.PROD}"
                    }
			    }
            }
            post {
                success {
                    echo 'success create application-prod'
                }
                failure {
                    error 'fail create application-prod'
                }
            }
        }
        stage('build project') {
            steps {
                sh '''
        		 ./gradlew bootJar
        		 '''
            }
            post {
                success {
                    echo 'success build project'
                }
                failure {
                    error 'fail build project' // exit pipeline
                }
            }
        }
        // 도커 이미지를 만든다. build number로 태그를 주되 latest 태그도 부여한다.
        stage('dockerizing project') {
            steps {
                sh '''
        		 docker build -t $IMAGE_NAME:$BUILD_NUMBER .
        		 docker tag $IMAGE_NAME:$BUILD_NUMBER $IMAGE_NAME:latest
        		 '''
            }
            post {
                success {
                    echo 'success dockerizing project'
                }
                failure {
                    error 'fail dockerizing project' // exit pipeline
                }
            }
        }
        // 빌드넘버 태그와 latest 태그 둘 다 올린다.
        stage('upload aws ECR') {
            steps {
                script{
                    // cleanup current user docker credentials
                    sh 'rm -f ~/.dockercfg ~/.docker/config.json || true'

                    docker.withRegistry("https://${ECR_PATH}", "ecr:${REGION}:${AWS_CREDENTIAL_NAME}") {
                      docker.image("${IMAGE_NAME}:${BUILD_NUMBER}").push()
                      docker.image("${IMAGE_NAME}:latest").push()
                    }
                }
            }
            post {
                success {
                    echo 'success upload image'
                }
                failure {
                    error 'fail upload image' // exit pipeline
                }
            }
        }
        stage('image pull from ECR') {
            steps {
                sshagent (credentials: ['belloga-batch']) {
                sh """
                    ssh -o StrictHostKeyChecking=no ubuntu@10.0.239.184 '
                    aws ecr get-login-password --region ap-northeast-2 | docker login --username AWS --password-stdin 023778162658.dkr.ecr.ap-northeast-2.amazonaws.com
                    docker pull ${IMAGE_NAME}:latest
                    '
                """
                }
            }
        }
        stage('remove old container') {
            steps {
                sshagent (credentials: ['belloga-batch']) {
                sh """
                    ssh -o StrictHostKeyChecking=no ubuntu@10.0.239.184 '
                    docker rm -f belloga-labeling-verification-service
                    '
                """
                }
            }
        }

        stage('docker run') {
            steps {
                sshagent (credentials: ['belloga-batch']) {
                sh """
                    ssh -o StrictHostKeyChecking=no ubuntu@10.0.239.184 '
                    docker run --name $CONTAINER_NAME -e "SPRING_PROFILES_ACTIVE=$PROFILE" -e "TZ=$TIME_ZONE" -d -t $IMAGE_NAME
                    '
                """
                }
            }
        }
    }
}