package com.mentors.mentorsmate.application.port.usecase;

import com.mentors.mentorsmate.application.dto.EvaluationCreateRequest;
import com.mentors.mentorsmate.application.dto.EvaluationResponse;

import java.util.UUID;

public interface EvaluationUseCase {
    EvaluationResponse createEvaluation(UUID mateId, UUID mentoringId, EvaluationCreateRequest request);
}
