package com.devnus.belloga.labelingverification.verification.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "labeled_ocr_bounding_box")
@Getter
@NoArgsConstructor
public class LabeledOCRBoundingBox {
    @Id
    @Column(name = "labeled_ocr_bounding_box_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ocr_bounding_box_id")
    private Long ocrBoundingBoxId;

    @Column(name = "labeled_count")
    private Long labeledCount;

    @Builder
    public LabeledOCRBoundingBox(Long OCRBoundingBoxId) {
        this.ocrBoundingBoxId = OCRBoundingBoxId;
        this.labeledCount = 0L;
    }

    public void increaseLabeledCount(){
        this.labeledCount++;
    }
}
