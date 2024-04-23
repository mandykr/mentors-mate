package com.mentors.mentorsmate.application.port.output;

import com.mentors.mentorsmate.domain.entity.MentoringEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EvaluationRepository extends JpaRepository<MentoringEvaluation, UUID> {
}
