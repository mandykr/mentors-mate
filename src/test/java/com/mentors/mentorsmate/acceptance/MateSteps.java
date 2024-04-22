package com.mentors.mentorsmate.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class MateSteps {
    private static final String ENDPOINT = "/mates";

    public static ExtractableResponse<Response> 메이트_요청(String mentorId, String menteeId) {
        Map<String, String> params = new HashMap<>();
        params.put("mentorId", mentorId);
        params.put("menteeId", menteeId);
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().post(ENDPOINT)
                .then().log().all().extract();
    }

    public static void 메이트_요청됨(ExtractableResponse<Response> response) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(response.jsonPath().getString("status")).isEqualTo("DEMANDED")
        );
    }

    public static ExtractableResponse<Response> 메이트_수락(ExtractableResponse<Response> response) {
        String uri = response.header("Location");

        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put(uri + "/accept")
                .then().log().all().extract();
    }

    public static void 메이트_수락됨(ExtractableResponse<Response> response) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("status")).isEqualTo("ACCEPTED")
        );
    }

    public static ExtractableResponse<Response> 메이트_취소(ExtractableResponse<Response> response) {
        String uri = response.header("Location");

        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put(uri + "/cancel")
                .then().log().all().extract();
    }

    public static void 메이트_취소됨(ExtractableResponse<Response> response) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("status")).isEqualTo("CANCELLED")
        );
    }
}
