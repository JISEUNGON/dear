package com.dearbella.server.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "hospital_member")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class HospitalMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hospital_member_id")
    private Long hospitalMemberId;

    @Column(name = "hospital_id", columnDefinition = "int")
    private Long hospitalId;

    @Column(name = "hospital_name", length = 100, columnDefinition = "varchar")
    private String hospitalName;

    @Column(name = "memberId", columnDefinition = "bigint")
    private Long memberId;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
