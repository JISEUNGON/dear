package com.dearbella.server.repository;

import com.dearbella.server.domain.HospitalMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HospitalMemberRepository extends JpaRepository<HospitalMember, Long> {
    public Optional<HospitalMember> findByHospitalIdAndMemberId(Long hospitalId, Long memberId);
}
