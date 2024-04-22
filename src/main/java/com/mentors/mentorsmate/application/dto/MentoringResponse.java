package com.mentors.mentorsmate.application.dto;

import com.mentors.mentorsmate.domain.entity.Mentoring;
import com.mentors.mentorsmate.domain.vo.MentoringStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MentoringResponse {
    private UUID id;
    private UUID mateId;
    private MentoringStatus status;
    private LocalDateTime startDateTime;
    private int hour;

    public static MentoringResponse of(Mentoring mentoring) {
        return new MentoringResponse(
                mentoring.getId(),
                mentoring.getMate().getId(),
                mentoring.getStatus(),
                mentoring.getStartDateTime().getStartDateTime(),
                mentoring.getHour().getHour()
        );
    }
}
