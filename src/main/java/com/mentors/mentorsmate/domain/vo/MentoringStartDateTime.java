package com.mentors.mentorsmate.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@RequiredArgsConstructor
public class MentoringStartDateTime {
    private final LocalDateTime startDateTime;

//    public boolean willBePastLimit(MentoringHour hour, int limit) {
//        return !getEndDateTime(hour).isBefore(getLimitDateTime(limit));
//    }
//
//    private LocalDateTime getEndDateTime(MentoringHour hour) {
//        return startDateTime.plusHours(hour.getHour());
//    }
//
//    private LocalDateTime getLimitDateTime(int limit) {
//        return startDateTime.truncatedTo(ChronoUnit.HOURS).withHour(limit);
//    }
}
