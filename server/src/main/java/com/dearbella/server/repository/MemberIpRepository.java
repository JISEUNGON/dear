package com.dearbella.server.repository;

import com.dearbella.server.domain.MemberIp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberIpRepository extends JpaRepository<MemberIp, Long> {
    public List<MemberIp> findByMemberId(Long id);
}
