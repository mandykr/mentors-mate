package com.mentors.mentorsmate.domain.entity;

import com.mentors.mentorsmate.domain.vo.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

import static com.mentors.mentorsmate.domain.entity.Mentoring.createDemanded;
import static com.mentors.mentorsmate.domain.vo.MentoringStatus.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MentoringTest {
    private final UUID id = UUID.fromString("33cadde5-554e-46fc-846c-a220d6f21735");
    private MentoringStartDateTime startDateTime;
    private Mate mate;

    @BeforeEach
    void setup() {
        startDateTime = new MentoringStartDateTime(LocalDateTime.of(2024, Month.MARCH, 30, 10, 0));
        mate = Mate.createDemanded(UUID.fromString("3e4413d2-234c-4100-9559-5f041826d7ec"));
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
        Mate demandedMate = Mate.createDemanded(UUID.fromString("4a3879e6-1542-440b-bde1-29b0de7507d6"));
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

    @DisplayName("멘토링을 확정하면 상태를 확정으로 변경한다.")
    @Test
    void confirm() {
        Mentoring mentoring = createDemanded(id, startDateTime, new MentoringHour(3), mate);
        mentoring.confirm();
        assertThat(mentoring.getStatus()).isEqualTo(CONFIRMED);
    }

    @DisplayName("멘토링을 완료하면 상태를 완료로 변경한다.")
    @Test
    void complete() {
        Mentoring mentoring = createDemanded(id, startDateTime, new MentoringHour(3), mate);
        mentoring.complete();
        assertThat(mentoring.getStatus()).isEqualTo(COMPLETED);
    }

    @DisplayName("멘토링을 취소하면 상태를 취소로 변경한다.")
    @Test
    void cancel() {
        Mentoring mentoring = createDemanded(id, startDateTime, new MentoringHour(3), mate);
        mentoring.cancel();
        assertThat(mentoring.getStatus()).isEqualTo(CANCELLED);
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
