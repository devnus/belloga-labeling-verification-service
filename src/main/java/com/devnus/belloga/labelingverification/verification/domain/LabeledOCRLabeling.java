package com.devnus.belloga.labelingverification.verification.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "labeled_ocr_labeling")
@Getter
@NoArgsConstructor
public class LabeledOCRLabeling {
    @Id
    @Column(name = "labeled_ocr_labeling_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "labeled_ocr_text_label_id") // 연관관계의 주인
    private LabeledOCRTextLabel labeledOCRTextLabel;

    @Column(name = "labeling_uuid")
    private String labelingUUID;

    @Column(name = "verification_finish") //해당 바운딩 박스가 검증이 끝났는지 여부
    private Boolean verificationFinish;

    @Builder
    public LabeledOCRLabeling(LabeledOCRTextLabel labeledOCRTextLabel, String labelingUUID) {
        this.labeledOCRTextLabel = labeledOCRTextLabel;
        this.labelingUUID = labelingUUID;
        this.verificationFinish = false;
    }

    public void finishVerification() {
        this.verificationFinish = true;
        this.labeledOCRTextLabel.finishVerification(); //해당 바운딩 박스 라벨링의 조사가 끝났음을 확인
    }
}
