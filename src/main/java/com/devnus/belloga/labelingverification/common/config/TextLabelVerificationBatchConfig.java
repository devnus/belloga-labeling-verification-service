package com.devnus.belloga.labelingverification.common.config;

import com.devnus.belloga.labelingverification.verification.domain.LabeledOCRTextLabel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class TextLabelVerificationBatchConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    @Value(value = "${app.batch.chunk-size}")
    private int CHUNK_SIZE;

    @Value(value = "${app.batch.minimum-amount}")
    private long MINIMUM_AMOUNT;

    @Value(value = "${app.batch.success-reliability}")
    private double SUCCESS_RELIABILITY;

    /**
     * textLabel 검증 Job 생성
     */
    @Bean
    public Job textLabelVerificationJob() throws Exception {
        return jobBuilderFactory.get("textLabelVerificationJob")
                .start(textLabelVerificationStep())
                .build();
    }

    /**
     * textLabel 검증 Step 생성
     */
    @Bean
    @JobScope
    public Step textLabelVerificationStep() throws Exception {
        return stepBuilderFactory.get("textLabelVerificationStep")
                .<LabeledOCRTextLabel, LabeledOCRTextLabel> chunk(CHUNK_SIZE)
                .reader(textLabelVerificationReader(null))
                .processor(textLabelVerificationProcessor(null))
                .writer(textLabelVerificationWriter(null))
                .build();
    }

    /**
     * textLabel 검증 Step의 Reader
     * 하나의 바운딩 박스에 대한 라벨링 결과가 MINIMUM_AMOUNT 이상인것을 읽어온다
     */
    @Bean
    @StepScope
    public JpaPagingItemReader <LabeledOCRTextLabel> textLabelVerificationReader(@Value("#{jobParameters[date]}") String date) throws Exception {

        log.info("jobParameters value : " + date);

        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("amount",MINIMUM_AMOUNT);

        return new JpaPagingItemReaderBuilder<LabeledOCRTextLabel>()
                .pageSize(CHUNK_SIZE) //chunk size와 같게 하는것 권장
                .parameterValues(parameterValues)
                .queryString("SELECT t FROM LabeledOCRTextLabel t join fetch t.labeledOCRBoundingBox b WHERE b.labeledCount >= :amount ORDER BY t.id ASC")
                .entityManagerFactory(entityManagerFactory)
                .name("textLabelVerificationReader")
                .build();
    }

    /**
     * textLabel 검증 Step의 Processor
     * 같은 라벨링의 비율이 SUCCESS_RELIABILITY 이상인지 확인한다
     */
    @Bean
    @StepScope
    public ItemProcessor<LabeledOCRTextLabel, LabeledOCRTextLabel> textLabelVerificationProcessor(@Value("#{jobParameters[date]}") String date) {

        return new ItemProcessor<LabeledOCRTextLabel, LabeledOCRTextLabel>() {
            @Override
            public LabeledOCRTextLabel process(LabeledOCRTextLabel labeledOCRTextLabel) throws Exception {

                log.info("jobParameters value : " + date);

                //신뢰도 계산
                double reliability = ((double) labeledOCRTextLabel.getLabeledCount() / (double) labeledOCRTextLabel.getLabeledOCRBoundingBox().getLabeledCount());

                if(reliability >= SUCCESS_RELIABILITY) { //신뢰도가 80프로 이상일때
                    labeledOCRTextLabel.successVerification();
                } else { //신뢰도가 80프로 이하일때
                    labeledOCRTextLabel.failVerification();
                }

                return labeledOCRTextLabel;
            }
        };
    }

    /**
     * textLabel 검증 Step의 Writer
     */
    @Bean
    @StepScope
    public JpaItemWriter<LabeledOCRTextLabel> textLabelVerificationWriter(@Value("#{jobParameters[date]}") String date) throws Exception {

        log.info("jobParameters value : " + date);

        return new JpaItemWriterBuilder<LabeledOCRTextLabel>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

}
