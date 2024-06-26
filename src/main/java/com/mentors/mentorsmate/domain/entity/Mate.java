package com.mentors.mentorsmate.domain.entity;

import com.mentors.mentorsmate.domain.vo.MateStatus;
import com.mentors.mentorsmate.domain.vo.MentoringStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import java.util.UUID;

import static com.mentors.mentorsmate.domain.vo.MateStatus.*;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mate extends BaseEntity {
    @Column(columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private MateStatus status;

    private UUID mentorId;
    private UUID menteeId;

    public static Mate createDemanded(UUID id, UUID mentorId, UUID menteeId) {
        return new Mate(id, DEMANDED, mentorId, menteeId);
    }

    public void accept() {
        if (status != DEMANDED) {
            throw new RuntimeException();
        }
        this.status = ACCEPTED;
    }

    public void reject() {
        if (status != DEMANDED) {
            throw new RuntimeException();
        }
        this.status = REJECTED;
    }

    public void cancel(MentoringStatus mentoringStatus) {
        if (status != ACCEPTED) {
            throw new RuntimeException();
        }
        if (!mentoringStatus.canCancelMate()) {
            throw new RuntimeException();
        }
        this.status = CANCELLED;
    }

    public boolean cannotCreateMentoring() {
        return this.status.cannotCreateMentoring();
    }
}
