package com.dearbella.server.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "review_like")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ReviewLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id", columnDefinition = "int")
    private Long likeId;

    @Column(name = "review_id", columnDefinition = "int")
    private Long reviewId;

    @Column(name = "member_id", columnDefinition = "bigint")
    private Long memberId;
}
