package com.mentors.mentorsmate.application.port.usecase;

import com.mentors.mentorsmate.application.dto.MentoringCreateRequest;
import com.mentors.mentorsmate.application.dto.MentoringResponse;

import java.util.UUID;

public interface MentoringUseCase {
    MentoringResponse createMentoring(UUID mateId, MentoringCreateRequest request);

    MentoringResponse acceptMentoring(UUID mateId, UUID mentoringId);

    MentoringResponse confirmMentoring(UUID mateId, UUID mentoringId);

    MentoringResponse completeMentoring(UUID mateId, UUID mentoringId);

    MentoringResponse cancelMentoring(UUID mateId, UUID mentoringId);
}
