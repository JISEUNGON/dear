package com.dearbella.server.repository;

import com.dearbella.server.domain.Banner;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BannerRepository extends JpaRepository<Banner, Long> {
    public List<Banner> findBannerByBannerLocation(Boolean location, Sort sort);
}
