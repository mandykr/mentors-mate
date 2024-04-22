package com.mentors.mentorsmate.application.dto;

import com.mentors.mentorsmate.domain.entity.Mate;
import com.mentors.mentorsmate.domain.vo.MateStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MateResponse {
    private UUID id;
    private UUID mentorId;
    private UUID menteeId;
    private MateStatus status;

    public static MateResponse of(Mate mate) {
        return new MateResponse(
                mate.getId(),
                mate.getMentorId(),
                mate.getMenteeId(),
                mate.getStatus()
        );
    }
}
