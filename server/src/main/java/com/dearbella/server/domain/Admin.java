package com.dearbella.server.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Setter
@Table(name = "admin")
public class Admin {
    @Id
    @Column(name = "member_id", columnDefinition = "bigint")
    private Long memberId;

    @Column(name = "admin_id", length = 10, columnDefinition = "varchar")
    private String adminId;

    @Column(name = "admin_password", columnDefinition = "text")
    private String adminPassword;

    @Column(name = "hospital_name", length = 100, columnDefinition = "varchar")
    private String hospitalName;

    @Column(name = "hospital_id", columnDefinition = "int")
    private Long hospitalId;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
