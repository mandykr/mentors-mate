package com.mentors.mentorsmate.domain.entity;

import com.mentors.mentorsmate.domain.vo.MentoringScore;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MentoringEvaluation extends BaseEntity {
    @Column(columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Embedded
    private MentoringScore score;
    private String review;
}
