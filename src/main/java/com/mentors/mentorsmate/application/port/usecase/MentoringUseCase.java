package com.mentors.mentorsmate.application.port.usecase;

import com.mentors.mentorsmate.application.dto.MentoringCreateRequest;
import com.mentors.mentorsmate.application.dto.MentoringDetailsResponse;
import com.mentors.mentorsmate.application.dto.MentoringResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface MentoringUseCase {
    MentoringResponse createMentoring(UUID mateId, MentoringCreateRequest request);

    MentoringResponse acceptMentoring(UUID mateId, UUID mentoringId);

    MentoringResponse confirmMentoring(UUID mateId, UUID mentoringId);

    MentoringResponse completeMentoring(UUID mateId, UUID mentoringId);

    MentoringResponse cancelMentoring(UUID mateId, UUID mentoringId);

    Page<MentoringResponse> getAllMentoringBy(Pageable page, UUID mateId);

    MentoringDetailsResponse getMentoringDetails(UUID mateId, UUID mentoringId);
}
