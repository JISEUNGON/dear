package com.dearbella.server.service.post;

import com.dearbella.server.domain.*;
import com.dearbella.server.dto.request.post.PostAddRequestDto;
import com.dearbella.server.dto.response.post.*;
import com.dearbella.server.enums.post.TagEnum;
import com.dearbella.server.exception.member.MemberIdNotFoundException;
import com.dearbella.server.exception.post.PostIdNotFoundException;
import com.dearbella.server.exception.post.TagIdNotFoundException;
import com.dearbella.server.repository.*;
import com.dearbella.server.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Table;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final ImageRepository imageRepository;
    private final MemberRepository memberRepository;
    private final PostLikeRepository postLikeRepository;

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
                        .deleted(false)
                        .viewNum(0L)
                    .build()
        );
    }

    @Override
    @Transactional
    public List<PostResponseDto> findByMemberId() {
        List<PostResponseDto> responseDtoList = new ArrayList<>();

        final List<Post> byMemberId = postRepository.findByMemberIdAndDeletedFalse(JwtUtil.getMemberId(), Sort.by(Sort.Direction.DESC, "createdAt"));
        final Member member = memberRepository.findById(JwtUtil.getMemberId()).orElseThrow(
                () -> new MemberIdNotFoundException(JwtUtil.getMemberId().toString())
        );

        for(Post post: byMemberId) {
            responseDtoList.add(
                    PostResponseDto.builder()
                            .postId(post.getPostId())
                            .commentNum(0L)
                            .viewNum(post.getViewNum())
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

    @Override
    @Transactional
    public List<PostFindResponseDto> findAll(Long tagId) {
        List<PostFindResponseDto> responseDtos = new ArrayList<>();
        final List<Post> allByDeletedFalse = postRepository.findByTagAndDeletedFalse(
                Tag.builder()
                        .tagId(tagId)
                        .tagName(TagEnum.findByValue(tagId).name())
                        .build(), Sort.by(Sort.Direction.DESC, "createdAt")
        );

        for(Post post: allByDeletedFalse) {
            final Member member = memberRepository.findById(post.getMemberId()).orElseThrow(
                    () -> new MemberIdNotFoundException(post.getMemberId().toString())
            );

            responseDtos.add(
                    PostFindResponseDto.builder()
                            .postId(post.getPostId())
                            .content(post.getContent())
                            .createdAt(post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss")))
                            .images(post.getPostImages())
                            .memberId(post.getMemberId())
                            .memberImage(member.getProfileImg())
                            .memberName(member.getNickname())
                            .title(post.getTitle())
                            .viewNum(post.getViewNum())
                            .likeNum((long) postLikeRepository.findByPostId(post.getPostId()).size())
                            .commentNum(0L)
                            .build()
            );
        }

        return responseDtos;
    }

    @Override
    @Transactional
    public PostDetailResponseDto findById(final Long postId) {
        final Post post = postRepository.findById(postId).orElseThrow(
                () -> new PostIdNotFoundException(postId)
        );
        final List<PostLike> byPostId = postLikeRepository.findByPostId(postId);
        final Member member = memberRepository.findById(post.getMemberId()).orElseThrow(
                () -> new MemberIdNotFoundException(post.getMemberId().toString())
        );
        Optional<PostLike> postLike = null;
        Boolean isLike = false;

        if(JwtUtil.isExistAccessToken() != null)
            postLike = postLikeRepository.findByMemberId(JwtUtil.getMemberId());

        isLike = postLike != null && !postLike.isEmpty() ? true : false;

        return PostDetailResponseDto.builder()
                .postId(postId)
                .category(post.getTag().getTagName().replace("_", " "))
                .likeCount(Long.valueOf(byPostId.size()))
                .memberId(post.getMemberId())
                .memberImage(member.getProfileImg())
                .memberName(member.getNickname())
                .viewCount(post.getViewNum())
                .isLike(isLike)
                .commentNum(0L)
                .createdAt(post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss")))
                .images(post.getPostImages())
                .content(post.getContent())
                .build();
    }

    @Override
    @Transactional
    public void addViewNum(final Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new PostIdNotFoundException(postId)
        );

        post.setViewNum(post.getViewNum() + 1);

        postRepository.save(post);
    }

    @Override
    @Transactional
    public String deletePost(final Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new PostIdNotFoundException(postId)
        );

        if(post.getDeleted())
            return "already deleted";
        else
            post.setDeleted(true);
        postRepository.save(post);

        return "deleted";
    }

    @Override
    @Transactional
    public String likePost(final Long id) {
        final Optional<PostLike> byPostIdAndMemberId = postLikeRepository.findByPostIdAndMemberId(id, JwtUtil.getMemberId());

        if(byPostIdAndMemberId.isEmpty()) {
            postLikeRepository.save(
                    PostLike.builder()
                            .postId(id)
                            .memberId(JwtUtil.getMemberId())
                            .build()
            );

            return "save";
        }
        else
        {
            postLikeRepository.deleteById(byPostIdAndMemberId.get().getPostLikeId());

            return "delete";
        }
    }

    @Override
    @Transactional
    public List<PostAdminResponseDto> findAllByCategory(Long category, Long page) {
        Page<Post> posts = postRepository.findByTagAndDeletedFalse(
                Tag.builder()
                        .tagId(category)
                        .tagName(TagEnum.findByValue(category).name())
                        .build(), PageRequest.of(page.intValue(), 11, Sort.by(Sort.Direction.DESC, "createdAt"))
        );
        List<PostAdminResponseDto> responseDtoList = new ArrayList<>();

        for(Post post: posts) {
            responseDtoList.add(
                    PostAdminResponseDto.builder()
                            .postId(post.getPostId())
                            .postName(post.getTitle())
                            .totalPage((long) posts.getTotalPages())
                            .build()
            );
        }

        return responseDtoList;
    }

    @Override
    @Transactional
    public PostAdminDetailResponseDto getDetail(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new PostIdNotFoundException(postId)
        );
        Member member = memberRepository.findById(post.getMemberId()).orElseThrow(
                () -> new MemberIdNotFoundException(post.getMemberId().toString())
        );

        return PostAdminDetailResponseDto.builder()
                .postId(postId)
                .content(post.getContent())
                .createdAt(post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyy-MM-dd HH-mm:ss")))
                .images(post.getPostImages())
                .memberId(post.getMemberId())
                .tag(post.getTag())
                .memberName(member.getNickname())
                .memberImage(member.getProfileImg())
                .build();
    }
}
