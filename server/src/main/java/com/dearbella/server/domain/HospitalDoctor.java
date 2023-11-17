package com.dearbella.server.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "hospital_doctor")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class HospitalDoctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hospital_doctor_id", columnDefinition = "int")
    private Long hospitalDoctorId;

    @Column(name = "hospital_name", length = 100, columnDefinition = "varchar")
    private String hospitalName;

    @Column(name = "doctor_id", columnDefinition = "int")
    private Long doctorId;
}
