package com.mentors.mentorsmate.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.LocalDateTime;

@Embeddable
@Getter
@EqualsAndHashCode
public class MentoringHour {
    private static final int MIN_HOUR = 1;
    private static final int MAX_HOUR = 15;

    @Column(name = "mentoring_hour")
    private final int hour;

    protected MentoringHour() {
        this.hour = 0;
    }

    public MentoringHour(int hour) {
        this.validate(hour);
        this.hour = hour;
    }

    private void validate(int hour) {
        if (hour < MIN_HOUR || hour > MAX_HOUR) {
            throw new RuntimeException();
        }
    }

    public boolean pastMidnight(MentoringStartDateTime startDateTime) {
        return startDateTime.plusHour(hour)
                .isAfter(startDateTime.getMidnight());
    }
}
