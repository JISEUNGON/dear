package com.dearbella.server.repository;

import com.dearbella.server.domain.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    public List<Doctor> findDoctorByDoctorName(String name);
}
