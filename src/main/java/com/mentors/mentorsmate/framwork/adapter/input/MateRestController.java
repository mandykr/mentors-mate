package com.mentors.mentorsmate.framwork.adapter.input;

import com.mentors.mentorsmate.application.dto.MateCreateRequest;
import com.mentors.mentorsmate.application.dto.MateResponse;
import com.mentors.mentorsmate.application.dto.MentoringCreateRequest;
import com.mentors.mentorsmate.application.dto.MentoringResponse;
import com.mentors.mentorsmate.application.port.usecase.MateUseCase;
import com.mentors.mentorsmate.application.port.usecase.MentoringUseCase;
import lombok.RequiredArgsConstructor;
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
}
