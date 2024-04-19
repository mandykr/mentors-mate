package com.mentors.mentorsmate.domain.entity;

import com.mentors.mentorsmate.domain.vo.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

import static com.mentors.mentorsmate.domain.entity.Mentoring.createDemanded;
import static com.mentors.mentorsmate.domain.vo.MentoringStatus.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.EnumSource.Mode.EXCLUDE;

class MentoringTest {
    private final UUID id = UUID.fromString("33cadde5-554e-46fc-846c-a220d6f21735");
    private final UUID mentorId = UUID.fromString("7b384a92-6fee-431a-bf01-499864903489");
    private final UUID menteeId = UUID.fromString("b77a7259-36bf-435a-9331-cdfd8e44fec8");
    private MentoringStartDateTime startDateTime;
    private Mate mate;

    @BeforeEach
    void setup() {
        startDateTime = new MentoringStartDateTime(LocalDateTime.of(2024, Month.MARCH, 30, 10, 0));
        mate = Mate.createDemanded(id, mentorId, menteeId);
        ReflectionTestUtils.setField(mate, "status", MateStatus.ACCEPTED);
    }

    @DisplayName("멘토링은 최초에 요청 상태를 갖는다.")
    @Test
    void demand() {
        Mentoring mentoring = createDemanded(id, startDateTime, new MentoringHour(3), mate);
        assertThat(mentoring.getStatus()).isEqualTo(DEMANDED);
    }

    @DisplayName("멘토링은 메이트가 수락 상태가 아니면 생성할 수 없다.")
    @Test
    void can_not_create_unfit_mate_status() {
        Mate demandedMate = Mate.createDemanded(id, mentorId, menteeId);
        assertThatThrownBy(() -> createDemanded(id, startDateTime, new MentoringHour(3), demandedMate))
                .isExactlyInstanceOf(RuntimeException.class);
    }

    @DisplayName("멘토링을 수락하면 상태를 수락으로 변경한다.")
    @Test
    void accept() {
        Mentoring mentoring = createDemanded(id, startDateTime, new MentoringHour(3), mate);
        mentoring.accept();
        assertThat(mentoring.getStatus()).isEqualTo(ACCEPTED);
    }

    @DisplayName("멘토링이 요청 상태가 아니면 수락할 수 없다.")
    @ParameterizedTest
    @EnumSource(mode = EXCLUDE, names = {"DEMANDED"})
    void can_not_accept_unfit_mentoring_status(MentoringStatus status) {
        Mentoring mentoring = createDemanded(id, startDateTime, new MentoringHour(3), mate);
        ReflectionTestUtils.setField(mentoring, "status", status);
        assertThatThrownBy(mentoring::accept)
                .isExactlyInstanceOf(RuntimeException.class);
    }

    @DisplayName("멘토링을 확정하면 상태를 확정으로 변경한다.")
    @Test
    void confirm() {
        Mentoring mentoring = createDemanded(id, startDateTime, new MentoringHour(3), mate);
        ReflectionTestUtils.setField(mentoring, "status", ACCEPTED);
        mentoring.confirm();
        assertThat(mentoring.getStatus()).isEqualTo(CONFIRMED);
    }

    @DisplayName("멘토링이 수락 상태가 아니면 확정할 수 없다.")
    @ParameterizedTest
    @EnumSource(mode = EXCLUDE, names = {"ACCEPTED"})
    void can_not_confirm_unfit_mentoring_status(MentoringStatus status) {
        Mentoring mentoring = createDemanded(id, startDateTime, new MentoringHour(3), mate);
        ReflectionTestUtils.setField(mentoring, "status", status);
        assertThatThrownBy(mentoring::confirm)
                .isExactlyInstanceOf(RuntimeException.class);
    }

    @DisplayName("멘토링을 완료하면 상태를 완료로 변경한다.")
    @Test
    void complete() {
        Mentoring mentoring = createDemanded(id, startDateTime, new MentoringHour(3), mate);
        ReflectionTestUtils.setField(mentoring, "status", CONFIRMED);
        mentoring.complete();
        assertThat(mentoring.getStatus()).isEqualTo(COMPLETED);
    }

    @DisplayName("멘토링이 확정 상태가 아니면 완료할 수 없다.")
    @ParameterizedTest
    @EnumSource(mode = EXCLUDE, names = {"CONFIRMED"})
    void can_not_complete_unfit_mentoring_status(MentoringStatus status) {
        Mentoring mentoring = createDemanded(id, startDateTime, new MentoringHour(3), mate);
        ReflectionTestUtils.setField(mentoring, "status", status);
        assertThatThrownBy(mentoring::complete)
                .isExactlyInstanceOf(RuntimeException.class);
    }

    @DisplayName("멘토링을 취소하면 상태를 취소로 변경한다.")
    @Test
    void cancel() {
        Mentoring mentoring = createDemanded(id, startDateTime, new MentoringHour(3), mate);
        mentoring.cancel();
        assertThat(mentoring.getStatus()).isEqualTo(CANCELLED);
    }

    @DisplayName("멘토링이 요청이나 수락 상태가 아니면 취소할 수 없다.")
    @ParameterizedTest
    @EnumSource(mode = EXCLUDE, names = {"DEMANDED", "ACCEPTED"})
    void can_not_cancel_unfit_mentoring_status(MentoringStatus status) {
        Mentoring mentoring = createDemanded(id, startDateTime, new MentoringHour(3), mate);
        ReflectionTestUtils.setField(mentoring, "status", status);
        assertThatThrownBy(mentoring::cancel)
                .isExactlyInstanceOf(RuntimeException.class);
    }

    @DisplayName("평가를 등록한다.")
    @Test
    void registerEvaluation() {
        Mentoring mentoring = createDemanded(id, startDateTime, new MentoringHour(3), mate);
        ReflectionTestUtils.setField(mentoring, "status", COMPLETED);

        mentoring.registerEvaluation(new MentoringEvaluation(
                UUID.fromString("077b9a9f-dfbf-4f4a-874a-c93cad6b1367"), new MentoringScore(2), "great"));

        assertThat(mentoring.getEvaluation()).isNotNull();
        assertThat(mentoring.getEvaluation().getReview()).isEqualTo("great");
    }

    @DisplayName("멘토링이 완료 상태가 아니면 평가를 등록할 수 없다.")
    @Test
    void not_completed() {
        Mentoring mentoring = createDemanded(id, startDateTime, new MentoringHour(3), mate);
        assertThatThrownBy(() -> mentoring.registerEvaluation(new MentoringEvaluation(
                UUID.fromString("077b9a9f-dfbf-4f4a-874a-c93cad6b1367"), new MentoringScore(2), "great")))
                .isExactlyInstanceOf(RuntimeException.class);
    }
}
