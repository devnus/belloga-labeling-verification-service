package com.devnus.belloga.labelingverification.verification.event;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VerificationProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value(value = "${app.topic.verification.fail-verify-labeling}")
    private String FAIL_VERIFY_LABELING_TOPIC;

    @Value(value = "${app.topic.verification.success-verify-labeling}")
    private String SUCCESS_VERIFY_LABELING_TOPIC;

    @Value(value = "${app.topic.verification.success-verify-text-label}")
    private String SUCCESS_VERIFY_TEXT_LABEL_TOPIC;

    /**
     * 신뢰도 확인 결과 라벨링(UUID)이 실패 했을때
     */
    public void failVerifyLabeling(Object event) {
        kafkaTemplate.send(FAIL_VERIFY_LABELING_TOPIC, event);
    }

    /**
     * 신뢰도 확인 결과 라벨링(UUID)이 성공 했을때
     */
    public void successVerifyLabeling(Object event) {
        kafkaTemplate.send(SUCCESS_VERIFY_LABELING_TOPIC, event);
    }

    /**
     * 신뢰도 확인 결과 텍스트 라벨의 검증이 성공 되었을때
     */
    public void successVerifyTextLabel(Object event) {
        kafkaTemplate.send(SUCCESS_VERIFY_TEXT_LABEL_TOPIC, event);
    }
}
