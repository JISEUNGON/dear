package com.dearbella.server.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "hospital_review")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class HospitalReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hospital_review_id", columnDefinition = "int")
    private Long hospitalReviewId;

    @Column(name = "hospital_id", columnDefinition = "int")
    private Long hospitalId;

    @Column(name = "review_id", columnDefinition = "int")
    private Long reviewId;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
