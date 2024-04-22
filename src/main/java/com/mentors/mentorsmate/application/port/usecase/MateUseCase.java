package com.mentors.mentorsmate.application.port.usecase;

import com.mentors.mentorsmate.application.dto.MateCreateRequest;
import com.mentors.mentorsmate.application.dto.MateResponse;

import java.util.UUID;

public interface MateUseCase {

    MateResponse createDemandedMate(MateCreateRequest request);

    MateResponse acceptMate(UUID mateId);

    MateResponse rejectMate(UUID mateId);

    MateResponse cancelMate(UUID mateId);
}
