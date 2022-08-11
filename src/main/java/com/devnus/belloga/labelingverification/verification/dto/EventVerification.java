package com.devnus.belloga.labelingverification.verification.dto;

import com.devnus.belloga.labelingverification.verification.domain.DataType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class EventVerification {

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FailVerifyLabeling {
        private DataType dataType;
        private String labelingUUID;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SuccessVerifyLabeling {
        private DataType dataType;
        private Long boundingBoxId;
        private Long totalLabelerNum;
        private Double accuracy;
        private String textLabel;
        private String labelingUUID;
    }
}
