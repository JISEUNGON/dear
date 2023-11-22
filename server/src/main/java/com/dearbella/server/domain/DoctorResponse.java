package com.dearbella.server.domain;

import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "doctor_response")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class DoctorResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "response_id", columnDefinition = "int")
    private Long responseId;

    @Column(name = "admin_id", columnDefinition = "bigint")
    private Long adminId;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "post_id", columnDefinition = "int")
    private Long postId;
}
