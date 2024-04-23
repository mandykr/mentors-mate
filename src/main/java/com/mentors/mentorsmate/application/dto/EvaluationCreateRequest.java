package com.mentors.mentorsmate.application.dto;

import com.mentors.mentorsmate.domain.entity.MentoringEvaluation;
import com.mentors.mentorsmate.domain.vo.EvaluationScore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Getter
public class EvaluationCreateRequest {
    private int score;
    private String review;

    public MentoringEvaluation to() {
        return new MentoringEvaluation(
                UUID.randomUUID(),
                new EvaluationScore(score),
                review);
    }
}
