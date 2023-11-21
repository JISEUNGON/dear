package com.dearbella.server.repository;

import com.dearbella.server.domain.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    public List<Inquiry> findByMemberId(Long memberId);
}
