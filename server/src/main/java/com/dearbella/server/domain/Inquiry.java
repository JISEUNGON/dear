package com.dearbella.server.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inquiry")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Inquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquiry_id", columnDefinition = "int")
    private Long inquiryId;

    @Column(name = "member_id", columnDefinition = "bigint")
    private Long memberId;

    @Column(name = "title", columnDefinition = "text")
    private String title;

    @Column(name = "category", columnDefinition = "int")
    private Long category;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    @Column(name = "answer", columnDefinition = "text")
    private String answer;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
