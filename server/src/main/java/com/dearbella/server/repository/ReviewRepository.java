package com.dearbella.server.repository;

import com.dearbella.server.domain.Review;
import com.dearbella.server.enums.doctor.CategoryEnum;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    /**
     * title
     * content
     * hospital
     * doctor
     * */
    public List<Review> findByTitleContainingAndDeletedFalse(String byValue, Sort sort);
}
