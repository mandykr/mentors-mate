package com.mentors.mentorsmate.domain.entity;

import com.mentors.mentorsmate.domain.vo.MentoringHour;
import com.mentors.mentorsmate.domain.vo.MentoringStartDateTime;
import com.mentors.mentorsmate.domain.vo.MentoringStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static com.mentors.mentorsmate.domain.vo.MentoringStatus.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mentoring {
    @Id
    private UUID id;

    @Column(name = "mentoring_status")
    @Enumerated(EnumType.STRING)
    private MentoringStatus status;

    @Embedded
    private MentoringStartDateTime startDateTime;

    @Embedded
    private MentoringHour hour;

    @OneToOne
    private MentoringEvaluation evaluation;

    private Mentoring(UUID id, MentoringStatus status, MentoringStartDateTime startDateTime, MentoringHour hour) {
        this.id = id;
        this.status = status;
        this.startDateTime = startDateTime;
        this.hour = hour;
    }

    public static Mentoring createDemanded(UUID id, MentoringStartDateTime startDateTime, MentoringHour hour) {
        return new Mentoring(id, DEMANDED, startDateTime, hour);
    }

    public void accept() {
        this.status = ACCEPTED;
    }

    public void confirm() {
        this.status = CONFIRMED;
    }

    public void complete() {
        this.status = COMPLETED;
    }

    public void cancel() {
        this.status = CANCELLED;
    }

    public void registerEvaluation(MentoringEvaluation evaluation) {
        if (this.status != COMPLETED) {
            throw new RuntimeException();
        }
        this.evaluation = evaluation;
    }
}