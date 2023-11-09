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
    @Column(name = "admin_id", length = 200, columnDefinition = "varchar")
    private String adminId;

    @Column(name = "admin_password", length = 50, columnDefinition = "varchar")
    private String adminPassword;

    @Column(name = "hospital_name", length = 100, columnDefinition = "varchar")
    private String hospitalName;
}
