package com.dearbella.server.service.post;

import com.dearbella.server.domain.Post;
import com.dearbella.server.dto.request.post.PostAddRequestDto;
import com.dearbella.server.dto.response.post.PostAdminResponseDto;
import com.dearbella.server.dto.response.post.PostDetailResponseDto;
import com.dearbella.server.dto.response.post.PostFindResponseDto;
import com.dearbella.server.dto.response.post.PostResponseDto;

import java.util.List;

public interface PostService {
    public Post savePost(PostAddRequestDto dto, List<String> images);
    public List<PostResponseDto> findByMemberId();

    public List<PostFindResponseDto> findAll(Long tagId);

    public PostDetailResponseDto findById(Long postId);

    public void addViewNum(Long postId);

    public String deletePost(Long postId);

    public String likePost(Long id);
    public List<PostAdminResponseDto> findAllByCategory(Long category, Long page);
}
