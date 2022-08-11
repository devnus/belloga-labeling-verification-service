package com.devnus.belloga.labelingverification.verification.service;

import com.devnus.belloga.labelingverification.verification.dto.EventLabeledData;

public interface VerificationService {
    boolean saveLabeledData(EventLabeledData.LabelingOCRBoundingBox event);
}
