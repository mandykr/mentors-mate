package com.mentors.mentorsmate.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class MentoringSteps {
    private static final String ENDPOINT = "/mates";

    public static ExtractableResponse<Response> 멘토링_요청(
            ExtractableResponse<Response> response,
            LocalDateTime startDateTime,
            int hour) {
        String mateId = response.jsonPath().getString("id");
        Map<String, String> params = new HashMap<>();
        params.put("startDateTime", startDateTime.toString());
        params.put("hour", hour + "");

        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().post(ENDPOINT + "/{mateId}/mentorings", mateId)
                .then().log().all().extract();
    }

    public static void 멘토링_요청됨(ExtractableResponse<Response> response) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(response.jsonPath().getString("status")).isEqualTo("DEMANDED")
        );
    }

    public static ExtractableResponse<Response> 멘토링_수락(ExtractableResponse<Response> response) {
        String uri = response.header("Location");

        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put(uri + "/accept")
                .then().log().all().extract();
    }

    public static void 멘토링_수락됨(ExtractableResponse<Response> response) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("status")).isEqualTo("ACCEPTED")
        );
    }

    public static ExtractableResponse<Response> 멘토링_확정(ExtractableResponse<Response> response) {
        String id = response.jsonPath().getString("id");
        String mateId = response.jsonPath().getString("mateId");

        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put(ENDPOINT + "/{mateId}/mentorings/{mentoringId}/confirm", mateId, id)
                .then().log().all().extract();
    }

    public static void 멘토링_확정됨(ExtractableResponse<Response> response) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("status")).isEqualTo("CONFIRMED")
        );
    }

    public static ExtractableResponse<Response> 멘토링_완료(ExtractableResponse<Response> response) {
        String id = response.jsonPath().getString("id");
        String mateId = response.jsonPath().getString("mateId");

        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put(ENDPOINT + "/{mateId}/mentorings/{mentoringId}/complete", mateId, id)
                .then().log().all().extract();
    }

    public static void 멘토링_완료됨(ExtractableResponse<Response> response) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("status")).isEqualTo("COMPLETED")
        );
    }

    public static ExtractableResponse<Response> 멘토링_평가_등록(
            ExtractableResponse<Response> response,
            int score,
            String review) {
        String mateId = response.jsonPath().getString("mateId");
        String id = response.jsonPath().getString("id");

        Map<String, String> params = new HashMap<>();
        params.put("score", score + "");
        params.put("review", review);

        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().post(ENDPOINT + "/{mateId}/mentorings/{mentoringId}/evaluations", mateId, id)
                .then().log().all().extract();
    }

    public static void 멘토링_평가_등록됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    public static ExtractableResponse<Response> 멘토링_상세_조회(ExtractableResponse<Response> response) {
        String mateId = response.jsonPath().getString("mateId");
        String id = response.jsonPath().getString("id");

        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(ENDPOINT + "/{mateId}/mentorings/{mentoringId}", mateId, id)
                .then().log().all().extract();
    }

    public static void 멘토링_상세_조회됨(ExtractableResponse<Response> response) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("id")).isNotEmpty()
        );
    }

    public static ExtractableResponse<Response> 멘토링_목록_조회(ExtractableResponse<Response> response) {
        String mateId = response.jsonPath().getString("id");

        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("page", "0")
                .queryParam("size", "10")
                .when().get(ENDPOINT + "/{mateId}/mentorings", mateId)
                .then().log().all().extract();
    }

    public static void 멘토링_목록_조회됨(ExtractableResponse<Response> response) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getInt("numberOfElements")).isGreaterThan(0)
        );
    }
}
