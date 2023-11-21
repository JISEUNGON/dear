package com.dearbella.server.domain;

import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", columnDefinition = "int")
    private Long commentId;

    @Column(name = "description", length = 10, columnDefinition = "varchar")
    private String description;

    @Column(name = "member_id", columnDefinition = "bigint")
    private Long memberId;

    @Column(name = "id", columnDefinition = "int")
    private Long id;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    @Column(name = "parent_comment", columnDefinition = "int")
    private Long parentComment;

    @Column(name = "deleted", columnDefinition = "tinyint")
    private Boolean deleted;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
