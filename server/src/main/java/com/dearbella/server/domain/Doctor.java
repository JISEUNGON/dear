package com.dearbella.server.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "doctor")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id", columnDefinition = "int")
    private Long doctorId;

    @Column(name = "doctor_name", length = 100, columnDefinition = "varchar")
    private String doctorName;

    @Column(name = "doctor_image", columnDefinition = "text")
    private String doctorImage;

    @Column(name = "hospital_name", length = 100, columnDefinition = "varchar")
    private String hospitalName;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "tag", length = 50, columnDefinition = "varchar")
    private String tag;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "doctor_career",
            joinColumns = {@JoinColumn(name = "doctor_id", referencedColumnName = "doctor_id")},
            inverseJoinColumns = {@JoinColumn(name = "career_id", referencedColumnName = "career_id")})
    @ApiModelProperty(example = "경력")
    private Set<Career> career;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "doctor_link",
            joinColumns = {@JoinColumn(name = "doctor_id", referencedColumnName = "doctor_id")},
            inverseJoinColumns = {@JoinColumn(name = "link_id", referencedColumnName = "link_id")})
    @ApiModelProperty(example = "영상 링크 주")
    private Set<IntroLink> links;

    @Column(name = "admin_id", columnDefinition = "int")
    private Long adminId;

    @Column(name = "created_at", columnDefinition = "datetime")
    private LocalDateTime createdAt;
}
