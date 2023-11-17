package com.dearbella.server.repository;

import com.dearbella.server.domain.HospitalDoctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HospitalDoctorRepository extends JpaRepository<HospitalDoctor, Long> {
    public List<HospitalDoctor> findByHospitalName(String name);
}
