package com.mentors.mentorsmate.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class MentoringHourTest {

    @DisplayName("1미만 이거나 15초과의 시간으로는 생성할 수 없다.")
    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 16})
    void less_than_1_or_more_than_15(int hour) {
        assertThatThrownBy(() -> new MentoringHour(hour))
                .isExactlyInstanceOf(RuntimeException.class);
    }
}
