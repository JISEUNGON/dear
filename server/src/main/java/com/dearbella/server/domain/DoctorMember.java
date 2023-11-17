package com.dearbella.server.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "doctor_member")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DoctorMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_member_id", columnDefinition = "int")
    private Long doctorMemberId;

    @Column(name = "doctor_id", columnDefinition = "int")
    private Long doctorId;

    @Column(name = "member_id", columnDefinition = "bigint")
    private Long memberId;
}
