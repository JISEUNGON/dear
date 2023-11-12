package com.dearbella.server.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "doctor_review")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DoctorReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_review_id", columnDefinition = "int")
    private Long doctorReviewId;

    @Column(name = "doctor_id", columnDefinition = "int")
    private Long doctorId;

    @Column(name = "review_id", columnDefinition = "int")
    private Long reviewId;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
