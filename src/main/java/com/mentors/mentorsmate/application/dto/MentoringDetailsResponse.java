package com.mentors.mentorsmate.application.dto;

import com.mentors.mentorsmate.domain.entity.Mentoring;
import com.mentors.mentorsmate.domain.entity.MentoringEvaluation;
import com.mentors.mentorsmate.domain.vo.MentoringStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MentoringDetailsResponse {
    private UUID id;
    private UUID mateId;
    private MentoringStatus status;
    private LocalDateTime startDateTime;
    private int hour;
    private UUID evaluationId;
    private int score;
    private String review;

    public static MentoringDetailsResponse of(Mentoring mentoring) {
        MentoringEvaluation evaluation = mentoring.getEvaluation();
        return new MentoringDetailsResponse(
                mentoring.getId(),
                mentoring.getMate().getId(),
                mentoring.getStatus(),
                mentoring.getStartDateTime().getStartDateTime(),
                mentoring.getHour().getHour(),
                evaluation.getId(),
                evaluation.getScore().getScore(),
                evaluation.getReview()
        );
    }
}
