package com.mentors.mentorsmate.application.port.output;

import com.mentors.mentorsmate.domain.entity.Mentoring;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface MentoringRepository extends JpaRepository<Mentoring, UUID> {
    @Query("select mr" +
            " from Mentoring mr" +
            " where mr.mate.id = :mateId" +
            " order by mr.createdDate desc" +
            " limit 1")
    Mentoring findRecentByMateId(UUID mateId);
}
