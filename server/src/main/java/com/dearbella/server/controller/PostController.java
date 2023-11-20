package com.dearbella.server.controller;

import com.dearbella.server.domain.Member;
import com.dearbella.server.domain.Post;
import com.dearbella.server.dto.request.post.PostAddRequestDto;
import com.dearbella.server.dto.response.post.PostResponseDto;
import com.dearbella.server.service.member.MemberService;
import com.dearbella.server.service.post.PostService;
import com.dearbella.server.service.s3.S3UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
@Slf4j
@Api(tags = {"커뮤니티 API"})
public class PostController {
    private final PostService postService;
    private final S3UploadService s3UploadService;

    @PostMapping(value = "/add", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation("게시물 작성")
    @Transactional
    public ResponseEntity<Post> addPost(@ModelAttribute PostAddRequestDto dto) throws IOException {
        List<String> images = new ArrayList<>();

        for(MultipartFile file: dto.getImages()) {
            images.add(
                    s3UploadService.upload(file, "/dearbella/post", false)
            );
        }

        return ResponseEntity.ok(postService.savePost(dto, images));
    }

    @ApiOperation("내 게시물")
    @GetMapping("/my")
    public ResponseEntity<List<PostResponseDto>> getMyPosts() {
        return ResponseEntity.ok(postService.findByMemberId());
    }
}
