package com.devnus.belloga.labelingverification.verification.domain;

import com.devnus.belloga.labelingverification.common.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "labeled_ocr_bounding_box")
@Getter
@NoArgsConstructor
public class LabeledOCRBoundingBox extends BaseTimeEntity {
    @Id
    @Column(name = "labeled_ocr_bounding_box_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ocr_bounding_box_id")
    private Long ocrBoundingBoxId;

    @Column(name = "labeled_count")
    private Long labeledCount;

    @Column(name = "verification_finish") //해당 바운딩 박스가 검증이 끝났는지 여부
    private Boolean verificationFinish;

    @Builder
    public LabeledOCRBoundingBox(Long OCRBoundingBoxId) {
        this.ocrBoundingBoxId = OCRBoundingBoxId;
        this.labeledCount = 0L;
        this.verificationFinish = false;
    }

    public void increaseLabeledCount(){
        this.labeledCount++;
    }

    public void finishVerification() {
        this.verificationFinish = true;
    }
}
