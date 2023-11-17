package com.dearbella.server.repository;

import com.dearbella.server.domain.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    public Optional<Hospital> findByHospitalName(String name);
}
