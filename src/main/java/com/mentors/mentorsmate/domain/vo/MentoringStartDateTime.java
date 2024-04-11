package com.mentors.mentorsmate.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Embeddable
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class MentoringStartDateTime {
    private static final int MIDNIGHT = 0;
    private final LocalDateTime startDateTime;

    protected MentoringStartDateTime() {
        this.startDateTime = null;
    }

    public LocalDateTime plusHour(int hour) {
        return Objects.requireNonNull(startDateTime)
                .plusHours(hour);
    }

    public LocalDateTime getMidnight() {
        return truncateWithHour(MIDNIGHT)
                .plusDays(1);
    }

    private LocalDateTime truncateWithHour(int hour) {
        return Objects.requireNonNull(startDateTime)
                .truncatedTo(ChronoUnit.HOURS)
                .withHour(hour);
    }
}
