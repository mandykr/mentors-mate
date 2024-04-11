package com.mentors.mentorsmate.domain.vo;

public enum MentoringStatus {
    DEMANDED, ACCEPTED, CONFIRMED, COMPLETED, CANCELLED;

    public boolean canCancelMate() {
        return this == CANCELLED;
    }
}
