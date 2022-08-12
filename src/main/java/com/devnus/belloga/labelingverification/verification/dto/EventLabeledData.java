package com.devnus.belloga.labelingverification.verification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class EventLabeledData {
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LabelingOCRBoundingBox {
        private Long ocrBoundingBoxId;
        private String textLabel;
        private String labelingUUID;
    }
}
