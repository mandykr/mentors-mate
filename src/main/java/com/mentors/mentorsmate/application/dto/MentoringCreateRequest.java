package com.mentors.mentorsmate.application.dto;

import com.mentors.mentorsmate.domain.entity.Mate;
import com.mentors.mentorsmate.domain.entity.Mentoring;
import com.mentors.mentorsmate.domain.vo.MentoringHour;
import com.mentors.mentorsmate.domain.vo.MentoringStartDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class MentoringCreateRequest {
    private LocalDateTime startDateTime;
    private int hour;

    public Mentoring to(Mate mate) {
        return Mentoring.createDemanded(
                UUID.randomUUID(),
                new MentoringStartDateTime(startDateTime),
                new MentoringHour(hour),
                mate);
    }
}
