package com.dearbella.server.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "post_like")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_like_id", columnDefinition = "int")
    private Long postLikeId;

    @Column(name = "post_id", columnDefinition = "int")
    private Long postId;

    @Column(name = "member_id", columnDefinition = "bigint")
    private Long memberId;
}
