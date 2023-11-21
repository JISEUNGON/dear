package com.dearbella.server.repository;

import com.dearbella.server.domain.DoctorMember;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorMemberRepository extends JpaRepository<DoctorMember, Long> {
    public Optional<DoctorMember> findByDoctorIdAndMemberId(Long doctorId, Long memberId);
    public List<DoctorMember> findByMemberId(Long memberId, Sort sort);
    public void deleteByDoctorId(Long doctorId);
}
