package com.devnus.belloga.labelingverification.common.config;

import com.devnus.belloga.labelingverification.verification.domain.DataType;
import com.devnus.belloga.labelingverification.verification.domain.LabeledOCRLabeling;
import com.devnus.belloga.labelingverification.verification.dto.EventVerification;
import com.devnus.belloga.labelingverification.verification.event.VerificationProducer;
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

/**
 * 각 라벨링(UUID)별 검증 배치 Config
 * TestLabel에 따른 각 라벨링(UUID)의 통과 여부 확인
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class LabelingVerificationBatchConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    private final VerificationProducer verificationProducer;

    @Value(value = "${app.batch.chunk-size}")
    private int CHUNK_SIZE;

    /**
     * 라벨링(UUID)별 검증 Job 생성
     */
    @Bean
    public Job labelingVerificationJob() throws Exception {
        return jobBuilderFactory.get("labelingVerificationJob")
                .start(labelingVerificationStep())
                .build();
    }

    /**
     * 라벨링(UUID)별 검증 Step 생성
     */
    @Bean
    @JobScope
    public Step labelingVerificationStep() throws Exception {
        return stepBuilderFactory.get("labelingVerificationStep")
                .<LabeledOCRLabeling, LabeledOCRLabeling> chunk(CHUNK_SIZE)
                .reader(labelingVerificationReader(null))
                .processor(labelingVerificationProcessor(null))
                .writer(labelingVerificationWriter(null))
                .build();
    }

    /**
     * 라벨링(UUID)별 검증 Step의 Reader
     * textLabel의 신뢰도가 null이 아닌 라벨링 Read
     */
    @Bean
    @StepScope
    public JpaPagingItemReader<LabeledOCRLabeling> labelingVerificationReader(@Value("#{jobParameters[date]}") String date) throws Exception {

        log.info("jobParameters value : " + date);

        return new JpaPagingItemReaderBuilder<LabeledOCRLabeling>()
                .pageSize(10) //chunk size와 같게 하는것 권장
                .queryString("SELECT l FROM LabeledOCRLabeling l join fetch l.labeledOCRTextLabel t WHERE t.verification IS NOT NULL ORDER BY l.id ASC")
                .entityManagerFactory(entityManagerFactory)
                .name("labelingVerificationReader")
                .build();
    }

    /**
     * 라벨링(UUID)별 검증 Step의 Processor
     * 검증(true or false)에 따라 성공 또는 실패 토픽으로 이벤트 전달
     */
    @Bean
    @StepScope
    public ItemProcessor<LabeledOCRLabeling, LabeledOCRLabeling> labelingVerificationProcessor(@Value("#{jobParameters[date]}") String date) {

        return new ItemProcessor<LabeledOCRLabeling, LabeledOCRLabeling>() {
            @Override
            public LabeledOCRLabeling process(LabeledOCRLabeling labeledOCRLabeling) throws Exception {

                log.info("jobParameters value : " + date);

                if(labeledOCRLabeling.getLabeledOCRTextLabel().getVerification() == true) { //해당 라벨링이 신뢰성있다고 판단되었을때

                    EventVerification.SuccessVerifyLabeling event = EventVerification.SuccessVerifyLabeling.builder()
                            .dataType(DataType.OCR)
                            .labelingUUID(labeledOCRLabeling.getLabelingUUID())
                            .build();

                    verificationProducer.successVerifyLabeling(event);
                } else { //해당 라벨링이 신뢰없다고 판단되었을때

                    EventVerification.FailVerifyLabeling event = EventVerification.FailVerifyLabeling.builder()
                            .dataType(DataType.OCR)
                            .labelingUUID(labeledOCRLabeling.getLabelingUUID())
                            .build();

                    verificationProducer.failVerifyLabeling(event);
                }

                return labeledOCRLabeling;
            }
        };
    }

    /**
     * 라벨링(UUID)별 검증 Step의 Writer
     */
    @Bean
    @StepScope
    public JpaItemWriter<LabeledOCRLabeling> labelingVerificationWriter(@Value("#{jobParameters[date]}") String date) throws Exception {

        log.info("jobParameters value : " + date);

        return new JpaItemWriterBuilder<LabeledOCRLabeling>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }
}
