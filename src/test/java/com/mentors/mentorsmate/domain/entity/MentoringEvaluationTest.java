package com.mentors.mentorsmate.domain.entity;

import com.mentors.mentorsmate.domain.vo.EvaluationScore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatCode;

class MentoringEvaluationTest {
    @DisplayName("멘토링 평가는 식별자, 점수, 리뷰를 갖는다.")
    @Test
    void create() {
        assertThatCode(() -> new MentoringEvaluation(
                UUID.fromString("077b9a9f-dfbf-4f4a-874a-c93cad6b1367"), new EvaluationScore(2), "great"))
                .doesNotThrowAnyException();
    }
}
