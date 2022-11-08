package com.devnus.belloga.labelingverification.verification.domain;

import com.devnus.belloga.labelingverification.common.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "labeled_ocr_text_label")
@Getter
@NoArgsConstructor
public class LabeledOCRTextLabel extends BaseTimeEntity {
    @Id
    @Column(name = "labeled_ocr_text_label_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "labeled_ocr_bounding_box_id") // 연관관계의 주인
    private LabeledOCRBoundingBox labeledOCRBoundingBox;

    @Column(name = "text_label")
    private String textLabel;

    @Column(name = "labeled_count")
    private Long labeledCount;

    @Column(name = "verification")
    private Boolean verification;

    @Builder
    public LabeledOCRTextLabel(LabeledOCRBoundingBox labeledOCRBoundingBox, String textLabel) {
        this.labeledOCRBoundingBox = labeledOCRBoundingBox;
        this.textLabel = textLabel;
        this.labeledCount = 0L;
        this.verification = null;
    }

    public void successVerification(){
        verification = true;
    }

    public void failVerification() {
        verification = false;
    }

    public void increaseLabeledCount(){
        this.labeledCount++;
    }
}
