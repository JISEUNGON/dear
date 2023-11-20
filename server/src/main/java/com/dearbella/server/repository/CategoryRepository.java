package com.dearbella.server.repository;

import com.dearbella.server.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    public Optional<Category> findByCategoryName(String name);
}
