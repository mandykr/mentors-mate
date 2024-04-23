package com.mentors.mentorsmate.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.mentors.mentorsmate.acceptance.MateSteps.*;
import static com.mentors.mentorsmate.acceptance.MentoringSteps.*;
import static java.time.Month.MARCH;

public class MateAcceptanceTest extends AcceptanceTest {
    private String mentorId = "7b384a92-6fee-431a-bf01-499864903489";
    private String menteeId = "b77a7259-36bf-435a-9331-cdfd8e44fec8";

    /**
     * Feature: 멘토링을 진행
     *
     *   Scenario: 메이트를 맺고 멘토링을 진행한다.
     *     When 멘티가 멘토에게 메이트를 요청한다.
     *     Then 메이트가 요청된다.
     *     When 멘토가 메이트 요청을 수락한다.
     *     Then 메이트 요청이 수락된다.
     *     When 멘티가 멘토에게 일일 멘토링을 요청한다.
     *     When 멘토가 멘토링 요청을 수락한다.
     *     Then 멘토링 요청이 수락된다.
     *     When 멘티가 멘토링을 확정한다.
     *     Then 멘토링이 확정된다.
     *     When 메이트가 멘토링을 완료한다.
     *     Then 멘토링이 완료된다.
     *     When 멘티가 메이트를 취소한다.
     *     Then 메이트가 취소된다.
     *     When 멘티가 멘토링의 평가를 등록한다.
     *     Then 멘토링의 평가가 등록된다.
     *     When 멘토링 상세 정보를 조회한다.
     *     Then 멘토링 상세 정보가 조회된다.
     *     When 메이트의 멘토링 목록을 조회한다.
     *     Then 멘토링 목록이 조회된다.
     */
    @DisplayName("메이트를 맺고 멘토링을 진행한다.")
    @Test
    void mentoring() {
        ExtractableResponse<Response> demandedMate = 메이트_요청(mentorId, menteeId);
        메이트_요청됨(demandedMate);

        ExtractableResponse<Response> acceptedMate = 메이트_수락(demandedMate);
        메이트_수락됨(acceptedMate);

        LocalDateTime 시작_시간 = LocalDateTime.of(2024, MARCH, 30, 1, 30);
        int 멘토링_시간 = 3;
        ExtractableResponse<Response> demandedMentoring = 멘토링_요청(acceptedMate, 시작_시간, 멘토링_시간);
        멘토링_요청됨(demandedMentoring);

        ExtractableResponse<Response> acceptedMentoring = 멘토링_수락(demandedMentoring);
        멘토링_수락됨(acceptedMentoring);

        ExtractableResponse<Response> confirmedMentoring = 멘토링_확정(acceptedMentoring);
        멘토링_확정됨(confirmedMentoring);

        ExtractableResponse<Response> completedMentoring = 멘토링_완료(confirmedMentoring);
        멘토링_완료됨(completedMentoring);

        ExtractableResponse<Response> cancelMate = 메이트_취소(demandedMate);
        메이트_취소됨(cancelMate);

        int 평가_점수 = 3;
        String 평가_리뷰 = "무난했어요.";
        ExtractableResponse<Response> evaluation = 멘토링_평가_등록(confirmedMentoring, 평가_점수, 평가_리뷰);
        멘토링_평가_등록됨(evaluation);

        ExtractableResponse<Response> mentoringDetails = 멘토링_상세_조회(confirmedMentoring);
        멘토링_상세_조회됨(mentoringDetails);

        ExtractableResponse<Response> mentoringList = 멘토링_목록_조회(demandedMate);
        멘토링_목록_조회됨(mentoringList);
    }
}
