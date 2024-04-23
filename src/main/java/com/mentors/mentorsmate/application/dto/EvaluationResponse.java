package com.mentors.mentorsmate.application.dto;

import com.mentors.mentorsmate.domain.entity.MentoringEvaluation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EvaluationResponse {
    private UUID id;
    private int score;
    private String review;

    public static EvaluationResponse of(MentoringEvaluation evaluation) {
        return new EvaluationResponse(
                evaluation.getId(),
                evaluation.getScore().getScore(),
                evaluation.getReview());
    }
}
