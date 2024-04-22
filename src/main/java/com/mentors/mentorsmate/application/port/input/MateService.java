package com.mentors.mentorsmate.application.port.input;

import com.mentors.mentorsmate.application.dto.MateCreateRequest;
import com.mentors.mentorsmate.application.dto.MateResponse;
import com.mentors.mentorsmate.application.port.output.MateRepository;
import com.mentors.mentorsmate.application.port.output.MentoringRepository;
import com.mentors.mentorsmate.application.port.usecase.MateUseCase;
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
public class MateService implements MateUseCase {
    private final MateRepository mateRepository;
    private final MentoringRepository mentoringRepository;

    @Override
    public MateResponse createDemandedMate(MateCreateRequest request) {
        Mate mate = Mate.createDemanded(UUID.randomUUID(), request.getMentorId(), request.getMenteeId());
        return MateResponse.of(mateRepository.save(mate));
    }

    @Override
    public MateResponse acceptMate(UUID mateId) {
        Mate mate = findOrElseThrow(mateId);
        mate.accept();
        return MateResponse.of(mate);
    }

    @Override
    public MateResponse rejectMate(UUID mateId) {
        Mate mate = findOrElseThrow(mateId);
        mate.reject();
        return MateResponse.of(mate);
    }

    @Override
    public MateResponse cancelMate(UUID mateId) {
        Mate mate = findOrElseThrow(mateId);
        Mentoring mentoring = mentoringRepository.findRecentByMateId(mateId);
        mate.cancel(mentoring.getStatus());
        return MateResponse.of(mate);
    }

    private Mate findOrElseThrow(UUID mateId) {
        return mateRepository.findById(mateId)
                .orElseThrow(EntityNotFoundException::new);
    }
}
