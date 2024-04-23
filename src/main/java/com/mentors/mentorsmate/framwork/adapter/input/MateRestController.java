package com.mentors.mentorsmate.framwork.adapter.input;

import com.mentors.mentorsmate.application.dto.*;
import com.mentors.mentorsmate.application.port.usecase.EvaluationUseCase;
import com.mentors.mentorsmate.application.port.usecase.MateUseCase;
import com.mentors.mentorsmate.application.port.usecase.MentoringUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/mates")
@RestController
public class MateRestController {
    private final MateUseCase mateUseCase;
    private final MentoringUseCase mentoringUseCase;
    private final EvaluationUseCase evaluationUseCase;

    @PostMapping
    public ResponseEntity<MateResponse> createMate(@RequestBody MateCreateRequest request) {
        MateResponse mate = mateUseCase.createDemandedMate(request);
        return ResponseEntity.created(URI.create("/mates/" + mate.getId())).body(mate);
    }

    @PutMapping("/{mateId}/accept")
    public ResponseEntity<MateResponse> acceptMate(@PathVariable UUID mateId) {
        return ResponseEntity.ok(mateUseCase.acceptMate(mateId));
    }

    @PutMapping("/{mateId}/reject")
    public ResponseEntity<MateResponse> rejectMate(@PathVariable UUID mateId) {
        return ResponseEntity.ok(mateUseCase.rejectMate(mateId));
    }

    @PutMapping("/{mateId}/cancel")
    public ResponseEntity<MateResponse> cancelMate(@PathVariable UUID mateId) {
        return ResponseEntity.ok(mateUseCase.cancelMate(mateId));
    }

    @PostMapping("/{mateId}/mentorings")
    public ResponseEntity<MentoringResponse> createMentoring(
            @PathVariable UUID mateId,
            @RequestBody MentoringCreateRequest request) {
        MentoringResponse mentoring = mentoringUseCase.createMentoring(mateId, request);
        return ResponseEntity.created(
                URI.create("/mates/" + mentoring.getMateId() + "/mentorings/" + mentoring.getId()))
                .body(mentoring);
    }

    @PutMapping("/{mateId}/mentorings/{mentoringId}/accept")
    public ResponseEntity<MentoringResponse> acceptMentoring(
            @PathVariable UUID mateId,
            @PathVariable UUID mentoringId) {
        return ResponseEntity.ok(mentoringUseCase.acceptMentoring(mateId, mentoringId));
    }

    @PutMapping("/{mateId}/mentorings/{mentoringId}/confirm")
    public ResponseEntity<MentoringResponse> confirmMentoring(
            @PathVariable UUID mateId,
            @PathVariable UUID mentoringId) {
        return ResponseEntity.ok(mentoringUseCase.confirmMentoring(mateId, mentoringId));
    }

    @PutMapping("/{mateId}/mentorings/{mentoringId}/complete")
    public ResponseEntity<MentoringResponse> completeMentoring(
            @PathVariable UUID mateId,
            @PathVariable UUID mentoringId) {
        return ResponseEntity.ok(mentoringUseCase.completeMentoring(mateId, mentoringId));
    }

    @PutMapping("/{mateId}/mentorings/{mentoringId}/cancel")
    public ResponseEntity<MentoringResponse> cancelMentoring(
            @PathVariable UUID mateId,
            @PathVariable UUID mentoringId) {
        return ResponseEntity.ok(mentoringUseCase.cancelMentoring(mateId, mentoringId));
    }

    @PostMapping("/{mateId}/mentorings/{mentoringId}/evaluations")
    public ResponseEntity<EvaluationResponse> createEvaluation(
            @PathVariable UUID mateId,
            @PathVariable UUID mentoringId,
            @RequestBody EvaluationCreateRequest request) {
        EvaluationResponse evaluation = evaluationUseCase.createEvaluation(mateId, mentoringId, request);
        return ResponseEntity.created(URI.create("/mates/" + evaluation.getId())).body(evaluation);
    }

    @GetMapping("/{mateId}/mentorings")
    public ResponseEntity<Page<MentoringResponse>> getAllMentoringBy(
            @PageableDefault(size = 10, page = 0) Pageable page,
            @PathVariable UUID mateId) {
        return ResponseEntity.ok(mentoringUseCase.getAllMentoringBy(page, mateId));
    }

    @GetMapping("/{mateId}/mentorings/{mentoringId}")
    public ResponseEntity<MentoringDetailsResponse> getMentoringDetails(@PathVariable UUID mateId, @PathVariable UUID mentoringId) {
        return ResponseEntity.ok(mentoringUseCase.getMentoringDetails(mateId, mentoringId));
    }
}
