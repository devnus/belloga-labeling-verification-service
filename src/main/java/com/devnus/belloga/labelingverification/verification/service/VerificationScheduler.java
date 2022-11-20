package com.devnus.belloga.labelingverification.verification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class VerificationScheduler {

    private final JobLauncher jobLauncher;
    private final Job textLabelVerificationJob;
    private final Job labelingVerificationJob;

    /**
     * 매일 02시 0분 0초에 텍스트 라벨 신뢰도 검증 실행
     */
    @Scheduled(cron = "0 0/30 * * * *", zone = "Asia/Seoul")
    public void executeVerificationJob () throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobInstanceAlreadyCompleteException, JobRestartException {

        log.info("execute Verification Start!");

        Map<String, JobParameter> jobParameterMap = new HashMap<>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        Date time = new Date();
        String date = simpleDateFormat.format(time);

        jobParameterMap.put("date", new JobParameter(date));
        JobParameters parameters = new JobParameters(jobParameterMap);

        /**
         * 텍스트 라벨 신뢰도 검증
         */

        JobExecution textLabelVerificationJobExecution = jobLauncher.run(textLabelVerificationJob, parameters);

        while(textLabelVerificationJobExecution.isRunning()){
            log.info("Text Label Verification Job is running...");
        }

        log.info("TextLabel Verification Execution: " + textLabelVerificationJobExecution.getStatus());
        log.info("TextLabel Verification getJobConfigurationName: " + textLabelVerificationJobExecution.getJobConfigurationName());
        log.info("TextLabel Verification getJobId: " + textLabelVerificationJobExecution.getJobId());
        log.info("TextLabel Verification getExitStatus: " + textLabelVerificationJobExecution.getExitStatus());
        log.info("TextLabel Verification getJobInstance: " + textLabelVerificationJobExecution.getJobInstance());
        log.info("TextLabel Verification getStepExecutions: " + textLabelVerificationJobExecution.getStepExecutions());
        log.info("TextLabel Verification getLastUpdated: " + textLabelVerificationJobExecution.getLastUpdated());
        log.info("TextLabel Verification getFailureExceptions: " + textLabelVerificationJobExecution.getFailureExceptions());

        /**
         * 라벨링별 신뢰도 검증
         */

        JobExecution labelingVerificationJobExecution = jobLauncher.run(labelingVerificationJob, parameters);

        while(labelingVerificationJobExecution.isRunning()){
            log.info("Labeling Verification Job is running...");
        }

        log.info("Labeling Verification Execution: " + labelingVerificationJobExecution.getStatus());
        log.info("Labeling Verification getJobConfigurationName: " + labelingVerificationJobExecution.getJobConfigurationName());
        log.info("Labeling Verification getJobId: " + labelingVerificationJobExecution.getJobId());
        log.info("Labeling Verification getExitStatus: " + labelingVerificationJobExecution.getExitStatus());
        log.info("Labeling Verification getJobInstance: " + labelingVerificationJobExecution.getJobInstance());
        log.info("Labeling Verification getStepExecutions: " + labelingVerificationJobExecution.getStepExecutions());
        log.info("Labeling Verification getLastUpdated: " + labelingVerificationJobExecution.getLastUpdated());
        log.info("Labeling Verification getFailureExceptions: " + labelingVerificationJobExecution.getFailureExceptions());

        log.info("execute Verification Finish!");
    }
}
