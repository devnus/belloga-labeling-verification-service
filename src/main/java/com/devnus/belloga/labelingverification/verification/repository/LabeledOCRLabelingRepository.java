package com.devnus.belloga.labelingverification.verification.repository;

import com.devnus.belloga.labelingverification.verification.domain.LabeledOCRLabeling;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabeledOCRLabelingRepository extends JpaRepository<LabeledOCRLabeling, Long> {
}
