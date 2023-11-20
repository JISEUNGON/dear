package com.dearbella.server.service.post;

import com.dearbella.server.domain.Image;
import com.dearbella.server.domain.Member;
import com.dearbella.server.domain.Post;
import com.dearbella.server.domain.Tag;
import com.dearbella.server.dto.request.post.PostAddRequestDto;
import com.dearbella.server.dto.response.post.PostResponseDto;
import com.dearbella.server.exception.member.MemberIdNotFoundException;
import com.dearbella.server.exception.post.TagIdNotFoundException;
import com.dearbella.server.repository.ImageRepository;
import com.dearbella.server.repository.MemberRepository;
import com.dearbella.server.repository.PostRepository;
import com.dearbella.server.repository.TagRepository;
import com.dearbella.server.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Table;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final ImageRepository imageRepository;
    private final MemberRepository memberRepository;

    /**
     * Like Number
     * */
    @Override
    @Transactional
    public Post savePost(PostAddRequestDto dto, List<String> images) {
        Tag tag = tagRepository.findById(dto.getTag()).orElseThrow(
                () -> new TagIdNotFoundException(dto.getTag())
        );
        List<Image> imageList = new ArrayList<>();

        for(String image: images) {
            imageList.add(
                    imageRepository.save(
                            Image.builder()
                                    .memberId(JwtUtil.getMemberId())
                                    .imageUrl(image)
                                    .build()
                    )
            );
        }

        return postRepository.save(
                Post.builder()
                        .tag(tag)
                        .postImages(imageList)
                        .title(dto.getTitle())
                        .content(dto.getContent())
                        .memberId(JwtUtil.getMemberId())
                        .like(0L)
                    .build()
        );
    }

    @Override
    @Transactional
    public List<PostResponseDto> findByMemberId() {
        List<PostResponseDto> responseDtoList = new ArrayList<>();

        final List<Post> byMemberId = postRepository.findByMemberId(JwtUtil.getMemberId(), Sort.by(Sort.Direction.DESC, "createdAt"));
        final Member member = memberRepository.findById(JwtUtil.getMemberId()).orElseThrow(
                () -> new MemberIdNotFoundException(JwtUtil.getMemberId().toString())
        );

        for(Post post: byMemberId) {
            responseDtoList.add(
                    PostResponseDto.builder()
                            .postId(post.getPostId())
                            .commentNum(0L)
                            .viewNum(0L)
                            .likeNum(0L)
                            .name(member.getNickname())
                            .memberImage(member.getProfileImg())
                            .createdAt(post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                            .content(post.getContent())
                            .build()
            );
        }
        return responseDtoList;
    }
}
