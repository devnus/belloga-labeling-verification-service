package com.devnus.belloga.labelingverification.verification.event;

import com.devnus.belloga.labelingverification.verification.dto.EventLabeledData;
import com.devnus.belloga.labelingverification.verification.service.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class VerificationConsumer {
    private final VerificationService verificationService;
    /**
     * 사용자가 라벨링을 수행 했을 때 토픽으로 부터 받는 consume
     * group은 협의 후 결정
     */
    @KafkaListener(topics = "labeling-ocr-bounding-box", groupId = "labeling-ocr-bounding-box-1")
    protected boolean registerCustomAccountEnterprise(Object event) throws IOException {
        return verificationService.saveLabeledData((EventLabeledData.LabelingOCRBoundingBox) event);
    }
}
