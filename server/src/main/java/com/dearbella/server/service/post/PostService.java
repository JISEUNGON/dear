package com.dearbella.server.service.post;

import com.dearbella.server.domain.Post;
import com.dearbella.server.dto.request.post.PostAddRequestDto;

import java.util.List;

public interface PostService {
    public Post savePost(PostAddRequestDto dto, List<String> images);
}
