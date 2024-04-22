package com.mentors.mentorsmate.application.port.output;

import com.mentors.mentorsmate.domain.entity.Mate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MateRepository extends JpaRepository<Mate, UUID> {
}
