package com.mentors.mentorsmate.application.port.output;

import com.mentors.mentorsmate.domain.entity.Mentoring;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface MentoringRepository {

    Mentoring save(Mentoring mentoring);

    Optional<Mentoring> findById(UUID mentoringId);

    Mentoring findRecentByMateId(UUID mateId);

    Page<Mentoring> findAllByMateId(Pageable page, UUID mateId);

    Mentoring findWithEvaluationById(UUID id);
}
