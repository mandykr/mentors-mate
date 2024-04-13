package com.mentors.mentorsmate.domain.vo;

public enum MateStatus {
    DEMANDED, ACCEPTED, REJECTED, CANCELLED;

    public boolean cannotCreateMentoring() {
        return this != ACCEPTED;
    }
}
