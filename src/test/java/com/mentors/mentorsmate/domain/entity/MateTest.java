package com.mentors.mentorsmate.domain.entity;

import com.mentors.mentorsmate.domain.vo.MateStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.mentors.mentorsmate.domain.entity.Mate.createDemanded;
import static com.mentors.mentorsmate.domain.vo.MateStatus.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;

class MateTest {
    private UUID id = UUID.fromString("0ce594b5-456c-47b3-81ee-a0eb8686b840");

    @DisplayName("메이트는 식별자, 상태를 갖는다.")
    @Test
    void create() {
        assertThatCode(() -> createDemanded(id))
                .doesNotThrowAnyException();
    }

    @DisplayName("메이트는 최초에 요청 상태를 갖는다.")
    @Test
    void demand() {
        Mate mate = createDemanded(id);
        assertThat(mate.getStatus()).isEqualTo(DEMANDED);
    }

    @DisplayName("메이트를 수락하면 상태를 수락으로 변경한다.")
    @Test
    void accept() {
        Mate mate = createDemanded(id);
        mate.accept();
        assertThat(mate.getStatus()).isEqualTo(ACCEPTED);
    }

    @DisplayName("메이트를 거절하면 상태를 거절로 변경한다.")
    @Test
    void reject() {
        Mate mate = createDemanded(id);
        mate.reject();
        assertThat(mate.getStatus()).isEqualTo(REJECTED);
    }

    @DisplayName("메이트를 취소하면 상태를 취소로 변경한다.")
    @Test
    void cancel() {
        Mate mate = createDemanded(id);
        mate.cancel();
        assertThat(mate.getStatus()).isEqualTo(CANCELLED);
    }
}
