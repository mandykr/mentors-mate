package com.mentors.mentorsmate.domain.entity;

import com.mentors.mentorsmate.domain.vo.MateStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import java.util.UUID;

import static com.mentors.mentorsmate.domain.vo.MateStatus.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mate {
    private UUID id;
    private MateStatus status;

    public static Mate createDemanded(UUID id) {
        return new Mate(id, DEMANDED);
    }

    public void accept() {
        this.status = ACCEPTED;
    }

    public void reject() {
        this.status = REJECTED;
    }

    public void cancel() {
        this.status = CANCELLED;
    }
}
