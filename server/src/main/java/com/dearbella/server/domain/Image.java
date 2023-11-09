package com.dearbella.server.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "image")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id", columnDefinition = "int")
    private Long imageId;

    @Column(name = "image_url", columnDefinition = "text")
    private String imageUrl;

    @Column(name = "member_id", columnDefinition = "bigint")
    private Long memberId;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
