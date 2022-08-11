package com.devnus.belloga.labelingverification.verification.repository;

import com.devnus.belloga.labelingverification.verification.domain.LabeledOCRTextLabel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LabeledOCRTextLabelRepository extends JpaRepository<LabeledOCRTextLabel, Long> {
    Optional<LabeledOCRTextLabel> findByTextLabel(String textLabel);
}
