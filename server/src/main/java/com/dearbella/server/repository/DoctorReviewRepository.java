package com.dearbella.server.repository;

import com.dearbella.server.domain.DoctorReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorReviewRepository extends JpaRepository<DoctorReview, Long> {
    public List<DoctorReview> findDoctorReviewByDoctorId(Long id);
}
