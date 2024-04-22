package com.mentors.mentorsmate.application.port.input;

import com.mentors.mentorsmate.application.dto.MentoringCreateRequest;
import com.mentors.mentorsmate.application.dto.MentoringResponse;
import com.mentors.mentorsmate.application.port.output.MateRepository;
import com.mentors.mentorsmate.application.port.output.MentoringRepository;
import com.mentors.mentorsmate.application.port.usecase.MentoringUseCase;
import com.mentors.mentorsmate.domain.entity.Mate;
import com.mentors.mentorsmate.domain.entity.Mentoring;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Service
public class MentoringService implements MentoringUseCase {
    private final MentoringRepository mentoringRepository;
    private final MateRepository mateRepository;

    @Override
    public MentoringResponse createMentoring(UUID mateId, MentoringCreateRequest request) {
        Mate mate = findMateOrElseThrow(mateId);
        return MentoringResponse.of(mentoringRepository.save(request.to(mate)));
    }

    @Override
    public MentoringResponse acceptMentoring(UUID mateId, UUID mentoringId) {
        findMateOrElseThrow(mateId);
        Mentoring mentoring = findOrElseThrow(mentoringId);
        mentoring.accept();
        return MentoringResponse.of(mentoring);
    }

    @Override
    public MentoringResponse confirmMentoring(UUID mateId, UUID mentoringId) {
        findMateOrElseThrow(mateId);
        Mentoring mentoring = findOrElseThrow(mentoringId);
        mentoring.confirm();
        return MentoringResponse.of(mentoring);
    }

    @Override
    public MentoringResponse completeMentoring(UUID mateId, UUID mentoringId) {
        findMateOrElseThrow(mateId);
        Mentoring mentoring = findOrElseThrow(mentoringId);
        mentoring.complete();
        return MentoringResponse.of(mentoring);
    }

    @Override
    public MentoringResponse cancelMentoring(UUID mateId, UUID mentoringId) {
        findMateOrElseThrow(mateId);
        Mentoring mentoring = findOrElseThrow(mentoringId);
        mentoring.cancel();
        return MentoringResponse.of(mentoring);
    }

    private Mate findMateOrElseThrow(UUID mateId) {
        return mateRepository.findById(mateId)
                .orElseThrow(EntityNotFoundException::new);
    }

    private Mentoring findOrElseThrow(UUID mentoringId) {
        Mentoring mentoring = mentoringRepository.findById(mentoringId)
                .orElseThrow(EntityNotFoundException::new);
        return mentoring;
    }
}
