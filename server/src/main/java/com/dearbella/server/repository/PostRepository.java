package com.dearbella.server.repository;

import com.dearbella.server.domain.Category;
import com.dearbella.server.domain.Post;
import com.dearbella.server.domain.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    public List<Post> findByMemberId(Long memberId, Sort sort);
    public List<Post> findByTagAndDeletedFalse(Tag tag, Sort sort);
    public List<Post> findByMemberIdAndDeletedFalse(Long memberId, Sort sort);
    public Page<Post> findByTagAndDeletedFalse(Tag build, Pageable createdAt);
}
