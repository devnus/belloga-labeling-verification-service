package com.devnus.belloga.labelingverification.common.config;

import com.devnus.belloga.labelingverification.verification.repository.LabeledOCRTextLabelRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBatchTest
@SpringBootTest(classes={TextLabelVerificationBatchConfig.class, BatchTestConfig.class, KafkaTopicConfig.class, KafkaConsumerConfig.class})
@ActiveProfiles("test")
@EmbeddedKafka(
        brokerProperties = {
                "listeners=PLAINTEXT://localhost:9092"
        },
        ports = { 9092 })
class TextLabelVerificationBatchConfigTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private LabeledOCRTextLabelRepository labeledOCRTextLabelRepository;

    @Test
    public void textLabelVerificationJobTest() throws Exception {

        //given
        Map<String, JobParameter> jobParameterMap = new HashMap<>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        Date time = new Date();
        String date = simpleDateFormat.format(time);

        jobParameterMap.put("date", new JobParameter(date));
        JobParameters jobParameters = new JobParameters(jobParameterMap);

        //when
        JobExecution jobExecutionJob = jobLauncherTestUtils.launchJob(jobParameters);

        //then

        //batch ?????? ?????? ??????
        org.assertj.core.api.Assertions.assertThat(jobExecutionJob.getStatus()).isEqualTo(BatchStatus.COMPLETED);
        org.assertj.core.api.Assertions.assertThat(jobExecutionJob.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);

        //seed ??? ?????? ?????? ?????? ??????
        org.assertj.core.api.Assertions.assertThat(labeledOCRTextLabelRepository.findById(1L).get().getVerification()).isEqualTo(true);
        org.assertj.core.api.Assertions.assertThat(labeledOCRTextLabelRepository.findById(2L).get().getVerification()).isEqualTo(false);
        org.assertj.core.api.Assertions.assertThat(labeledOCRTextLabelRepository.findById(3L).get().getVerification()).isEqualTo(null);
    }
}