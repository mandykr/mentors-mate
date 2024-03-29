package com.mentors.mentorsmate.domain.vo;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode
public class MentoringScore {
    private static final int MIN_SCORE = 0;
    private static final int MAX_SCORE = 5;
    private final int score;

    protected MentoringScore() {
        this.score = -1;
    }

    public MentoringScore(int score) {
        this.validate(score);
        this.score = score;
    }

    private void validate(int score) {
        if (score < MIN_SCORE || score > MAX_SCORE) {
            throw new RuntimeException();
        }
    }
}
