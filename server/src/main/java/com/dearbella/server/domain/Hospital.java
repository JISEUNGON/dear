package com.dearbella.server.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "hospital")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hospital_id", columnDefinition = "int")
    private Long hospitalId;

    @Column(name = "hospital_video_link", columnDefinition = "text")
    private String hospitalVideoLink;

    @Column(name = "hospitalName", length = 100, columnDefinition = "varchar")
    private String hospitalName;

    @Column(name = "hospital_location", columnDefinition = "text")
    private String hospitalLocation;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "hospital_infra", length = 50, columnDefinition = "varchar")
    private String hospitalInfra;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "hospital_before",
            joinColumns = {@JoinColumn(name = "hospital_id", referencedColumnName = "hospital_id")},
            inverseJoinColumns = {@JoinColumn(name = "image_id", referencedColumnName = "image_id")})
    @ApiModelProperty(example = "병원 전 사진")
    private Set<Image> before;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "hospital_after",
            joinColumns = {@JoinColumn(name = "hospital_id", referencedColumnName = "hospital_id")},
            inverseJoinColumns = {@JoinColumn(name = "image_id", referencedColumnName = "image_id")})
    @ApiModelProperty(example = "병원 후 사")
    private Set<Image> after;

    @Column(name = "admin_id", columnDefinition = "int")
    private Long adminId;

    @CreationTimestamp
    private LocalDateTime createdAT;
}
