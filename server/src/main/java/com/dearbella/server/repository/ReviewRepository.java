package com.dearbella.server.repository;

import com.dearbella.server.domain.Review;
import com.dearbella.server.enums.doctor.CategoryEnum;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    /**
     * title
     * content
     * hospital
     * doctor
     * */
    public List<Review> findByTitleContainingAndDeletedFalse(String byValue, Sort sort);
    public List<Review> findByContentContainingAndDeletedFalse(String byValue, Sort sort);
    public List<Review> findByHospitalNameContainingAndDeletedFalse(String byValue, Sort sort);
    public List<Review> findByDoctorNameContainingAndDeletedFalse(String byValue, Sort sort);
    public List<Review> findByHospitalId(Long hospitalId);
    public List<Review> findByDoctorId(Long doctorId);
    public List<Review> findAllByMemberId(Long memberId, Sort sort);
}
