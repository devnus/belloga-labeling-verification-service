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
    //@Scheduled(cron = "0 0 2 * * *", zone = "Asia/Seoul")
    @Scheduled(cron = "0 0/5 0 * * *", zone = "Asia/Seoul") //개발 환경에서 5분마다 검증하도록 설정
    public void executeTextLabelVerificationJob () throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobInstanceAlreadyCompleteException, JobRestartException {
        Map<String, JobParameter> jobParameterMap = new HashMap<>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        Date time = new Date();
        String date = simpleDateFormat.format(time);

        jobParameterMap.put("date", new JobParameter(date));
        JobParameters parameters = new JobParameters(jobParameterMap);
        JobExecution jobExecution = jobLauncher.run(textLabelVerificationJob, parameters);

        while(jobExecution.isRunning()){
            log.info("Text Label Verification Job is running...");
        }

        log.info("Job Execution: " + jobExecution.getStatus());
        log.info("Job getJobConfigurationName: " + jobExecution.getJobConfigurationName());
        log.info("Job getJobId: " + jobExecution.getJobId());
        log.info("Job getExitStatus: " + jobExecution.getExitStatus());
        log.info("Job getJobInstance: " + jobExecution.getJobInstance());
        log.info("Job getStepExecutions: " + jobExecution.getStepExecutions());
        log.info("Job getLastUpdated: " + jobExecution.getLastUpdated());
        log.info("Job getFailureExceptions: " + jobExecution.getFailureExceptions());
    }

    /**
     * 매일 14시 0분 0초에 라벨링(UUID)별 검증 확인
     */
    //@Scheduled(cron = "0 0 14 * * *", zone = "Asia/Seoul")
    @Scheduled(cron = "0 0/6 0 * * *", zone = "Asia/Seoul") //개발 환경에서 6분마다 검증하도록 설정
    public void executeLabelingVerificationJob () throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobInstanceAlreadyCompleteException, JobRestartException {
        Map<String, JobParameter> jobParameterMap = new HashMap<>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        Date time = new Date();
        String date = simpleDateFormat.format(time);

        jobParameterMap.put("date", new JobParameter(date));
        JobParameters parameters = new JobParameters(jobParameterMap);
        JobExecution jobExecution = jobLauncher.run(labelingVerificationJob, parameters);

        while(jobExecution.isRunning()){
            log.info("Labeling Verification Job is running...");
        }

        log.info("Job Execution: " + jobExecution.getStatus());
        log.info("Job getJobConfigurationName: " + jobExecution.getJobConfigurationName());
        log.info("Job getJobId: " + jobExecution.getJobId());
        log.info("Job getExitStatus: " + jobExecution.getExitStatus());
        log.info("Job getJobInstance: " + jobExecution.getJobInstance());
        log.info("Job getStepExecutions: " + jobExecution.getStepExecutions());
        log.info("Job getLastUpdated: " + jobExecution.getLastUpdated());
        log.info("Job getFailureExceptions: " + jobExecution.getFailureExceptions());
    }
}
