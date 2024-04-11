package com.mentors.mentorsmate.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;
import java.time.Month;

import static java.time.Month.MARCH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MentoringHourTest {

    @DisplayName("1미만 이거나 15초과의 시간으로는 생성할 수 없다.")
    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 16})
    void less_than_1_or_more_than_15(int hour) {
        assertThatThrownBy(() -> new MentoringHour(hour))
                .isExactlyInstanceOf(RuntimeException.class);
    }

    @DisplayName("멘토링 시작 시간과 멘토링 시간의 합이 24를 초과하면 true를 반환한다.")
    @ParameterizedTest
    @CsvSource({"9, 1", "10, 0"})
    void past_midnight(int hour, int minute) {
        MentoringHour mentoringHour = new MentoringHour(15);
        MentoringStartDateTime startDateTime = new MentoringStartDateTime(
                LocalDateTime.of(2024, MARCH, 30, hour, minute));
        assertThat(mentoringHour.pastMidnight(startDateTime)).isTrue();
    }

    @DisplayName("멘토링 시작 시간과 멘토링 시간의 합이 24를 초과하지 않으면 false를 반환한다.")
    @ParameterizedTest
    @CsvSource({"9, 0", "8, 59"})
    void not_past_midnight(int hour, int minute) {
        MentoringHour mentoringHour = new MentoringHour(15);
        MentoringStartDateTime startDateTime = new MentoringStartDateTime(
                LocalDateTime.of(2024, MARCH, 30, hour, minute));
        assertThat(mentoringHour.pastMidnight(startDateTime)).isFalse();
    }
}
