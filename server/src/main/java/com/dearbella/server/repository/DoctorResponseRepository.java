package com.dearbella.server.repository;

import com.dearbella.server.domain.DoctorResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorResponseRepository extends JpaRepository<DoctorResponse, Long> {
}
