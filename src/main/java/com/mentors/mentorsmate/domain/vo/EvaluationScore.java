package com.mentors.mentorsmate.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Embeddable
@Getter
@EqualsAndHashCode
public class EvaluationScore {
    private static final int MIN_SCORE = 0;
    private static final int MAX_SCORE = 5;
    private final int score;

    protected EvaluationScore() {
        this.score = -1;
    }

    public EvaluationScore(int score) {
        this.validate(score);
        this.score = score;
    }

    private void validate(int score) {
        if (score < MIN_SCORE || score > MAX_SCORE) {
            throw new RuntimeException();
        }
    }
}
