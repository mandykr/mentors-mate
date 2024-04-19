package com.mentors.mentorsmate.domain.entity;

import com.mentors.mentorsmate.domain.vo.MentoringHour;
import com.mentors.mentorsmate.domain.vo.MentoringStartDateTime;
import com.mentors.mentorsmate.domain.vo.MentoringStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static com.mentors.mentorsmate.domain.vo.MentoringStatus.*;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mentoring extends BaseEntity {
    @Column(columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Column(name = "mentoring_status")
    @Enumerated(EnumType.STRING)
    private MentoringStatus status;

    @Embedded
    private MentoringStartDateTime startDateTime;

    @Embedded
    private MentoringHour hour;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mate_id",
            columnDefinition = "varbinary(16)",
            foreignKey = @ForeignKey(name = "fk_mentoring_to_mate"))
    private Mate mate;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "evaluation_id",
            columnDefinition = "varbinary(16)",
            foreignKey = @ForeignKey(name = "fk_mentoring_to_evaluation"))
    private MentoringEvaluation evaluation;

    private Mentoring(UUID id, MentoringStatus status, MentoringStartDateTime startDateTime, MentoringHour hour, Mate mate) {
        this.id = id;
        this.status = status;
        this.startDateTime = startDateTime;
        this.hour = hour;
        this.mate = mate;
    }

    public static Mentoring createDemanded(UUID id, MentoringStartDateTime startDateTime, MentoringHour hour, Mate mate) {
        validate(mate);
        return new Mentoring(id, DEMANDED, startDateTime, hour, mate);
    }

    private static void validate(Mate mate) {
        if (mate.cannotCreateMentoring()) {
            throw new RuntimeException();
        }
    }

    public void accept() {
        if (status != DEMANDED) {
            throw new RuntimeException();
        }
        this.status = ACCEPTED;
    }

    public void confirm() {
        if (status != ACCEPTED) {
            throw new RuntimeException();
        }
        this.status = CONFIRMED;
    }

    public void complete() {
        if (status != CONFIRMED) {
            throw new RuntimeException();
        }
        this.status = COMPLETED;
    }

    public void cancel() {
        if (status != DEMANDED && status != ACCEPTED) {
            throw new RuntimeException();
        }
        this.status = CANCELLED;
    }

    public void registerEvaluation(MentoringEvaluation evaluation) {
        if (this.status != COMPLETED) {
            throw new RuntimeException();
        }
        this.evaluation = evaluation;
    }
}
