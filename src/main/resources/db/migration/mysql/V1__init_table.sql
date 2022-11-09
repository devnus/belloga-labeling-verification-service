/* 라벨링된 OCR 바운딩 박스의 정보를 저장하는 테이블 */
CREATE TABLE labeled_ocr_bounding_box (
    labeled_ocr_bounding_box_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    ocr_bounding_box_id BIGINT NOT NULL,
    verification_finish BOOLEAN NOT NULL,
    created_date TIMESTAMP,
    last_modified_date TIMESTAMP,
    labeled_count BIGINT NOT NULL
);

/* 라벨링된 OCR 텍스트 라벨을 저장하는 테이블 */
CREATE TABLE labeled_ocr_text_label (
    labeled_ocr_text_label_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    labeled_ocr_bounding_box_id BIGINT NOT NULL,
    text_label VARCHAR(255),
    labeled_count BIGINT NOT NULL,
    verification BOOLEAN,
    created_date TIMESTAMP,
    last_modified_date TIMESTAMP,
    FOREIGN KEY (labeled_ocr_bounding_box_id) REFERENCES labeled_ocr_bounding_box (labeled_ocr_bounding_box_id)
);

/* 라벨링된 OCR 텍스트 라벨의 라벨링의 정보(UUID)를 저장하는 테이블 */
CREATE TABLE labeled_ocr_labeling (
    labeled_ocr_labeling_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    labeled_ocr_text_label_id BIGINT NOT NULL,
    labeling_uuid VARCHAR(255) NOT NULL,
    verification_finish BOOLEAN NOT NULL,
    created_date TIMESTAMP,
    last_modified_date TIMESTAMP,
    FOREIGN KEY (labeled_ocr_text_label_id) REFERENCES labeled_ocr_text_label (labeled_ocr_text_label_id)
);

/* 스프링 배치 메타 데이터를 저장하는 테이블 */
/* 3개의 시퀀스 테이블과 6개의 메타 테이블 */

/* 스프링 배치 SEQUENCE */
CREATE TABLE BATCH_STEP_EXECUTION_SEQ (ID BIGINT NOT NULL);
INSERT INTO BATCH_STEP_EXECUTION_SEQ values(0);
CREATE TABLE BATCH_JOB_EXECUTION_SEQ (ID BIGINT NOT NULL);
INSERT INTO BATCH_JOB_EXECUTION_SEQ values(0);
CREATE TABLE BATCH_JOB_SEQ (ID BIGINT NOT NULL);
INSERT INTO BATCH_JOB_SEQ values(0);

/* 스프링 배치 Job인스턴스를 저장하는 테이블 */
CREATE TABLE BATCH_JOB_INSTANCE (
    JOB_INSTANCE_ID BIGINT PRIMARY KEY ,
    VERSION BIGINT,
    JOB_NAME VARCHAR(100) NOT NULL ,
    JOB_KEY VARCHAR(2500)
);

/* JobExcution에 관련된 정보를 저장하는 테이블. (JobInstance가 실행 될 때마다 시작시간, 종료시간, 종료코드 등의 정보) */
CREATE TABLE BATCH_JOB_EXECUTION (
    JOB_EXECUTION_ID BIGINT PRIMARY KEY ,
    VERSION BIGINT,
    JOB_INSTANCE_ID BIGINT NOT NULL,
    CREATE_TIME TIMESTAMP NOT NULL,
    START_TIME TIMESTAMP DEFAULT NULL,
    END_TIME TIMESTAMP DEFAULT NULL,
    STATUS VARCHAR(10),
    EXIT_CODE VARCHAR(20),
    EXIT_MESSAGE VARCHAR(2500),
    LAST_UPDATED TIMESTAMP,
    JOB_CONFIGURATION_LOCATION VARCHAR(2500) NULL,
    constraint JOB_INSTANCE_EXECUTION_FK foreign key (JOB_INSTANCE_ID)
    references BATCH_JOB_INSTANCE(JOB_INSTANCE_ID)
);

/* Job을 실행 시킬 때 사용했던 JobParameters에 대한 정보를 저장하는 테이블 */
CREATE TABLE BATCH_JOB_EXECUTION_PARAMS (
    JOB_EXECUTION_ID BIGINT NOT NULL ,
    TYPE_CD VARCHAR(6) NOT NULL ,
    KEY_NAME VARCHAR(100) NOT NULL ,
    STRING_VAL VARCHAR(250) ,
    DATE_VAL DATETIME DEFAULT NULL ,
    LONG_VAL BIGINT ,
    DOUBLE_VAL DOUBLE PRECISION ,
    IDENTIFYING CHAR(1) NOT NULL ,
    constraint JOB_EXEC_PARAMS_FK foreign key (JOB_EXECUTION_ID)
    references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
);

/* StepExecution에 대한 정보를 저장하는 테이블. (읽은 수, 커밋 수, 스킵 수 등의 정보) */
CREATE TABLE BATCH_STEP_EXECUTION (
    STEP_EXECUTION_ID BIGINT PRIMARY KEY ,
    VERSION BIGINT NOT NULL,
    STEP_NAME VARCHAR(100) NOT NULL,
    JOB_EXECUTION_ID BIGINT NOT NULL,
    START_TIME TIMESTAMP NOT NULL ,
    END_TIME TIMESTAMP DEFAULT NULL,
    STATUS VARCHAR(10),
    COMMIT_COUNT BIGINT ,
    READ_COUNT BIGINT ,
    FILTER_COUNT BIGINT ,
    WRITE_COUNT BIGINT ,
    READ_SKIP_COUNT BIGINT ,
    WRITE_SKIP_COUNT BIGINT ,
    PROCESS_SKIP_COUNT BIGINT ,
    ROLLBACK_COUNT BIGINT ,
    EXIT_CODE VARCHAR(20) ,
    EXIT_MESSAGE VARCHAR(2500) ,
    LAST_UPDATED TIMESTAMP,
    constraint JOB_EXECUTION_STEP_FK foreign key (JOB_EXECUTION_ID)
    references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
);

/* JobExecution의 ExecutionContext 정보를 저장하는 테이블. (JobInstance가 실패 시 중단된 위치에서 다시 시작할 수 있는 정보) */
CREATE TABLE BATCH_JOB_EXECUTION_CONTEXT (
    JOB_EXECUTION_ID BIGINT PRIMARY KEY,
    SHORT_CONTEXT VARCHAR(2500) NOT NULL,
    SERIALIZED_CONTEXT CLOB,
    constraint JOB_EXEC_CTX_FK foreign key (JOB_EXECUTION_ID)
    references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
);

/* StepExecution의 ExecutionContext 정보를 저장하는 테이블. (JobInstance가 실패 시 중단된 위치에서 다시 시작할 수 있는 정보) */
CREATE TABLE BATCH_STEP_EXECUTION_CONTEXT (
    STEP_EXECUTION_ID BIGINT PRIMARY KEY,
    SHORT_CONTEXT VARCHAR(2500) NOT NULL,
    SERIALIZED_CONTEXT CLOB,
    constraint STEP_EXEC_CTX_FK foreign key (STEP_EXECUTION_ID)
    references BATCH_STEP_EXECUTION(STEP_EXECUTION_ID)
);
