package com.dearbella.server.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "banner")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "banner_id", columnDefinition = "int")
    private Long bannerId;

    @Column(name = "banner_location", columnDefinition = "tinyint")
    private Boolean bannerLocation;

    @Column(name = "banner_link", columnDefinition = "text")
    private String bannerLink;

    @Column(name = "hospital_name", length = 100, columnDefinition = "varchar")
    private String hospitalName;

    @Column(name = "main_image", columnDefinition = "text")
    private String mainImage;

    @Column(name = "hospital_location", length = 200, columnDefinition = "varchar")
    private String hospitalLocation;

    @Column(name = "description", length = 500, columnDefinition = "varchar")
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "banner_image",
            joinColumns = {@JoinColumn(name = "banner_id", referencedColumnName = "banner_id")},
            inverseJoinColumns = {@JoinColumn(name = "image_id", referencedColumnName = "image_id")})
    @ApiModelProperty(example = "banner images")
    private List<Image> bannerImages;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "banner_detail_image",
            joinColumns = {@JoinColumn(name = "banner_id", referencedColumnName = "banner_id")},
            inverseJoinColumns = {@JoinColumn(name = "image_id", referencedColumnName = "image_id")})
    @ApiModelProperty(example = "banner detail images")
    private List<Image> bannerDetailImages;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "banner_inra",
            joinColumns = {@JoinColumn(name = "banner_id", referencedColumnName = "banner_id")},
            inverseJoinColumns = {@JoinColumn(name = "infra_num", referencedColumnName = "infra_num")})
    @ApiModelProperty(example = "banner images")
    private List<Infra> bannerInfra;

    @Column(name = "admin_id", columnDefinition = "bigint")
    private Long adminId;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
