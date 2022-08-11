package com.devnus.belloga.labelingverification.verification.service;

import com.devnus.belloga.labelingverification.verification.domain.LabeledOCRBoundingBox;
import com.devnus.belloga.labelingverification.verification.domain.LabeledOCRLabeling;
import com.devnus.belloga.labelingverification.verification.domain.LabeledOCRTextLabel;
import com.devnus.belloga.labelingverification.verification.dto.EventLabeledData;
import com.devnus.belloga.labelingverification.verification.repository.LabeledOCRBoundingBoxRepository;
import com.devnus.belloga.labelingverification.verification.repository.LabeledOCRLabelingRepository;
import com.devnus.belloga.labelingverification.verification.repository.LabeledOCRTextLabelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {

    private final LabeledOCRBoundingBoxRepository labeledOCRBoundingBoxRepository;
    private final LabeledOCRTextLabelRepository labeledOCRTextLabelRepository;
    private final LabeledOCRLabelingRepository labeledOCRLabelingRepository;

    /**
     * 사용자가 라벨링을 수행 했을때
     * 이벤트로 온 바운딩 박스, 텍스트 라벨, 라벨링 UUID 저장
     * 신뢰도 검증에 사용
     */
    @Override
    @Transactional
    public boolean saveLabeledData(EventLabeledData.LabelingOCRBoundingBox event) {
        //이벤트로 온 바운딩 박스, 텍스트 존재 여부에 따른 저장
        LabeledOCRBoundingBox labeledOCRBoundingBox = labeledOCRBoundingBoxRepository.findByOcrBoundingBoxId(event.getOcrBoundingBoxId())
                .orElseGet(() -> labeledOCRBoundingBoxRepository.save(
                        LabeledOCRBoundingBox.builder()
                                .OCRBoundingBoxId(event.getOcrBoundingBoxId())
                                .build()
                ));
        labeledOCRBoundingBox.increaseLabeledCount(); //해당 바운딩 박스의 라벨링 횟수 증가

        LabeledOCRTextLabel labeledOCRTextLabel = labeledOCRTextLabelRepository.findByTextLabel(event.getTextLabel())
                .orElseGet(() -> labeledOCRTextLabelRepository.save(
                        LabeledOCRTextLabel.builder()
                                .textLabel(event.getTextLabel())
                                .labeledOCRBoundingBox(labeledOCRBoundingBox)
                                .build()
                ));
        labeledOCRTextLabel.increaseLabeledCount(); //해당 텍스트 라벨의 라벨링 횟수 증가

        labeledOCRLabelingRepository.save(LabeledOCRLabeling.builder()
                        .labeledOCRTextLabel(labeledOCRTextLabel)
                        .labelingUUID(event.getLabelingUUID())
                        .build());

        return true;
    }
}
