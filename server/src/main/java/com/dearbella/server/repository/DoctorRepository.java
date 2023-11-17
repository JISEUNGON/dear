package com.dearbella.server.repository;

import com.dearbella.server.domain.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    public List<Doctor> findDoctorByDoctorName(String name);

    public List<Doctor> findByHospitalName(String hospitalName);
}
