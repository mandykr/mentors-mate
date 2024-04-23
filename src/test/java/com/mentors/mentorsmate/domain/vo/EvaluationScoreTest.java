package com.mentors.mentorsmate.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EvaluationScoreTest {

    @DisplayName("멘토링 점수는 0미만 이거나 5초과면 등록할 수 없다.")
    @ParameterizedTest
    @ValueSource(ints = {-1, 6})
    void less_than_0_or_more_than_5(int score) {
        assertThatThrownBy(() -> new EvaluationScore(score))
                .isExactlyInstanceOf(RuntimeException.class);
    }
}
