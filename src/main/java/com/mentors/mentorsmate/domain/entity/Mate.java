package com.mentors.mentorsmate.domain.entity;

import com.mentors.mentorsmate.domain.vo.MateStatus;
import com.mentors.mentorsmate.domain.vo.MentoringStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
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
public class Mate {
    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private MateStatus status;

    public static Mate createDemanded(UUID id) {
        return new Mate(id, DEMANDED);
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
