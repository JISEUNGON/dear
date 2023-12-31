package com.dearbella.server.repository;

import com.dearbella.server.domain.Review;
import com.dearbella.server.enums.doctor.CategoryEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
    public List<Review> findByDoctorIdAndDeletedFalse(Long doctorId);
    public Page<Review> findByDeletedFalse(Pageable createdAt);
    public List<Review> findByMemberIdAndDeletedFalse(Long memberId);
    public List<Review> findByHospitalName(String hospitalName);
}
