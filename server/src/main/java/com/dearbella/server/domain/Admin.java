package com.dearbella.server.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Setter
@Table(name = "authority")
public class Admin {
    @Id
    @Column(name = "member_id", columnDefinition = "bigint")
    private Long memberId;

    @Column(name = "admin_id", length = 10, columnDefinition = "varchar")
    private String adminId;

    @Column(name = "admin_password", length = 12, columnDefinition = "varchar")
    private String adminPassword;

    @Column(name = "hospital_name", length = 100, columnDefinition = "varchar")
    private String hospitalName;

    @Column(name = "hospital_id", columnDefinition = "int")
    private Long hospitalId;
}
