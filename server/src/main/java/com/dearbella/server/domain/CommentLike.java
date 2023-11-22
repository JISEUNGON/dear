package com.dearbella.server.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment_like")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id", columnDefinition = "int")
    private Long likeId;

    @Column(name = "comment_id", columnDefinition = "int")
    private Long commentId;

    @Column(name = "member_id", columnDefinition = "bigint")
    private Long memberId;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
