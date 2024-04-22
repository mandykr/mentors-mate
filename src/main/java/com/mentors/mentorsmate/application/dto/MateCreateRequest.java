package com.mentors.mentorsmate.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Getter
public class MateCreateRequest {
    private UUID mentorId;
    private UUID menteeId;
}
