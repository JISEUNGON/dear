package com.dearbella.server.domain;

import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "review")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", columnDefinition = "int")
    private Long reviewId;

    @Column(name = "title", columnDefinition = "text")
    private String title;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    @Column(name = "deleted", columnDefinition = "tinyint")
    private Boolean deleted;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "member_id", columnDefinition = "bigint")
    private Long memberId;

    @Column(name = "hospital_id", columnDefinition = "int")
    private Long hospitalId;

    @Column(name = "hospital_name", length = 100, columnDefinition = "varchar")
    private String hospitalName;

    @Column(name = "doctor_id", columnDefinition = "int")
    private Long doctorId;

    @Column(name = "doctor_name", length = 100, columnDefinition = "varchar")
    private String doctorName;

    @Column(name = "rate", columnDefinition = "float")
    private Float rate;
}
