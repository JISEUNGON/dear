package com.dearbella.server.repository;

import com.dearbella.server.domain.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    public Page<Admin> findAll(Pageable pageable);
    public Optional<Admin> findByAdminId(String userId);
}
