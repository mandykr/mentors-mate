package com.mentors.mentorsmate.framwork.adapter.output;

import com.mentors.mentorsmate.application.port.output.MentoringRepository;
import com.mentors.mentorsmate.domain.entity.Mentoring;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MentoringJpaRepository extends MentoringRepository, MentoringQueryRepository, JpaRepository<Mentoring, UUID> {
    @Override
    @Query("select mr" +
            " from Mentoring mr" +
            " where mr.mate.id = :mateId" +
            " order by mr.createdDate desc" +
            " limit 1")
    Mentoring findRecentByMateId(UUID mateId);

    @Override
    @Query("select mr, me" +
            " from Mentoring mr" +
            " join fetch MentoringEvaluation me" +
            " on mr.evaluation.id = me.id" +
            " where mr.id = :id")
    Mentoring findWithEvaluationById(UUID id);
}
