package com.devnus.belloga.labelingverification.verification.repository;

import com.devnus.belloga.labelingverification.verification.domain.LabeledOCRBoundingBox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LabeledOCRBoundingBoxRepository extends JpaRepository<LabeledOCRBoundingBox, Long> {
    Optional<LabeledOCRBoundingBox> findByOcrBoundingBoxId(Long ocrBoundingBoxId);
}
