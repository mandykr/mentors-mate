package com.mentors.mentorsmate.domain.entity;

import com.mentors.mentorsmate.domain.vo.MateStatus;
import com.mentors.mentorsmate.domain.vo.MentoringStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

import static com.mentors.mentorsmate.domain.entity.Mate.createDemanded;
import static com.mentors.mentorsmate.domain.vo.MateStatus.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.params.provider.EnumSource.Mode.EXCLUDE;

class MateTest {
    private UUID id = UUID.fromString("0ce594b5-456c-47b3-81ee-a0eb8686b840");
    private UUID mentorId = UUID.fromString("7b384a92-6fee-431a-bf01-499864903489");
    private UUID menteeId = UUID.fromString("b77a7259-36bf-435a-9331-cdfd8e44fec8");

    @DisplayName("메이트는 식별자, 상태를 갖는다.")
    @Test
    void create() {
        assertThatCode(() -> createDemanded(id, mentorId, menteeId))
                .doesNotThrowAnyException();
    }

    @DisplayName("메이트는 최초에 요청 상태를 갖는다.")
    @Test
    void demand() {
        Mate mate = createDemanded(id, mentorId, menteeId);
        assertThat(mate.getStatus()).isEqualTo(DEMANDED);
    }

    @DisplayName("메이트를 수락하면 상태를 수락으로 변경한다.")
    @Test
    void accept() {
        Mate mate = createDemanded(id, mentorId, menteeId);
        mate.accept();
        assertThat(mate.getStatus()).isEqualTo(ACCEPTED);
    }

    @DisplayName("메이트가 요청 상태가 아니면 수락할 수 없다.")
    @ParameterizedTest
    @EnumSource(mode = EXCLUDE, names = {"DEMANDED"})
    void can_not_accept_unfit_mate_status(MateStatus status) {
        Mate mate = createDemanded(id, mentorId, menteeId);
        ReflectionTestUtils.setField(mate, "status", status);
        assertThatThrownBy(mate::accept)
                .isExactlyInstanceOf(RuntimeException.class);
    }

    @DisplayName("메이트를 거절하면 상태를 거절로 변경한다.")
    @Test
    void reject() {
        Mate mate = createDemanded(id, mentorId, menteeId);
        mate.reject();
        assertThat(mate.getStatus()).isEqualTo(REJECTED);
    }

    @DisplayName("메이트가 요청 상태가 아니면 거절할 수 없다.")
    @ParameterizedTest
    @EnumSource(mode = EXCLUDE, names = {"DEMANDED"})
    void can_not_reject_unfit_mate_status(MateStatus status) {
        Mate mate = createDemanded(id, mentorId, menteeId);
        ReflectionTestUtils.setField(mate, "status", status);
        assertThatThrownBy(mate::reject)
                .isExactlyInstanceOf(RuntimeException.class);
    }

    @DisplayName("메이트를 취소하면 상태를 취소로 변경한다.")
    @Test
    void cancel() {
        Mate mate = createDemanded(id, mentorId, menteeId);
        ReflectionTestUtils.setField(mate, "status", ACCEPTED);
        mate.cancel(MentoringStatus.CANCELLED);
        assertThat(mate.getStatus()).isEqualTo(CANCELLED);
    }

    @DisplayName("메이트가 수락 상태가 아니면 취소할 수 없다.")
    @ParameterizedTest
    @EnumSource(mode = EXCLUDE, names = {"ACCEPTED"})
    void can_not_cancel_unfit_mate_status(MateStatus status) {
        Mate mate = createDemanded(id, mentorId, menteeId);
        ReflectionTestUtils.setField(mate, "status", status);
        assertThatThrownBy(() -> mate.cancel(MentoringStatus.CANCELLED))
                .isExactlyInstanceOf(RuntimeException.class);
    }

    @DisplayName("멘토링이 완료, 취소 상태면 취소할 수 있다.")
    @ParameterizedTest
    @EnumSource(names = {"COMPLETED", "CANCELLED"})
    void can_cancel_unfit_mentoring_status(MentoringStatus mentoringStatus) {
        Mate mate = createDemanded(id, mentorId, menteeId);
        ReflectionTestUtils.setField(mate, "status", ACCEPTED);
        assertThatCode(() -> mate.cancel(mentoringStatus))
                .doesNotThrowAnyException();
    }

    @DisplayName("멘토링이 요청, 수락, 확정 상태면 취소할 수 없다.")
    @ParameterizedTest
    @EnumSource(names = {"DEMANDED", "ACCEPTED", "CONFIRMED"})
    void can_not_cancel_unfit_mentoring_status(MentoringStatus mentoringStatus) {
        Mate mate = createDemanded(id, mentorId, menteeId);
        ReflectionTestUtils.setField(mate, "status", ACCEPTED);
        assertThatThrownBy(() -> mate.cancel(mentoringStatus))
                .isExactlyInstanceOf(RuntimeException.class);
    }
}
