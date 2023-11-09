package com.dearbella.server.repository;

import com.dearbella.server.domain.IntroLink;
import io.swagger.v3.oas.models.links.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IntroLinkRepository extends JpaRepository<IntroLink, Link> {
}
