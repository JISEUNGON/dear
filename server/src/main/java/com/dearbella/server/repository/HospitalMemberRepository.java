package com.dearbella.server.repository;

import com.dearbella.server.domain.HospitalMember;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HospitalMemberRepository extends JpaRepository<HospitalMember, Long> {
    public Optional<HospitalMember> findByHospitalIdAndMemberId(Long hospitalId, Long memberId);
    public List<HospitalMember> findByMemberId(Long memberId, Sort sort);
}
