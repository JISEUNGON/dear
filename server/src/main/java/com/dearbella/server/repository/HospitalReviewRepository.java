package com.dearbella.server.repository;

import com.dearbella.server.domain.HospitalReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HospitalReviewRepository extends JpaRepository<HospitalReview, Long> {
    public List<HospitalReview> findHospitalReviewByHospitalId(Long id);
}
