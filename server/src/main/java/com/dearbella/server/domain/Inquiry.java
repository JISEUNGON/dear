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

    @Column(name = "hospital_id", columnDefinition = "int")
    private Long hospitalId;

    @Column(name = "member_name", length = 100, columnDefinition = "varchar")
    private String memberName;

    @Column(name = "location", columnDefinition = "int")
    private Long location;

    @Column(name = "phone_number", length = 20, columnDefinition = "varchar")
    private String phoneNumber;

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
