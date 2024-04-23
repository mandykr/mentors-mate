package com.mentors.mentorsmate.application.port.input;

import com.mentors.mentorsmate.application.dto.EvaluationCreateRequest;
import com.mentors.mentorsmate.application.dto.EvaluationResponse;
import com.mentors.mentorsmate.application.port.output.EvaluationRepository;
import com.mentors.mentorsmate.application.port.output.MateRepository;
import com.mentors.mentorsmate.application.port.output.MentoringRepository;
import com.mentors.mentorsmate.application.port.usecase.EvaluationUseCase;
import com.mentors.mentorsmate.domain.entity.Mate;
import com.mentors.mentorsmate.domain.entity.Mentoring;
import com.mentors.mentorsmate.domain.entity.MentoringEvaluation;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Service
public class EvaluationService implements EvaluationUseCase {
    private final MentoringRepository mentoringRepository;
    private final MateRepository mateRepository;
    private final EvaluationRepository evaluationRepository;

    @Override
    public EvaluationResponse createEvaluation(UUID mateId, UUID mentoringId, EvaluationCreateRequest request) {
        findMateOrElseThrow(mateId);
        Mentoring mentoring = findMentoringOrElseThrow(mentoringId);
        MentoringEvaluation evaluation = evaluationRepository.save(request.to());
        mentoring.registerEvaluation(evaluation);
        return EvaluationResponse.of(evaluation);
    }

    private Mate findMateOrElseThrow(UUID mateId) {
        return mateRepository.findById(mateId)
                .orElseThrow(EntityNotFoundException::new);
    }

    private Mentoring findMentoringOrElseThrow(UUID mentoringId) {
        return mentoringRepository.findById(mentoringId)
                .orElseThrow(EntityNotFoundException::new);
    }
}
