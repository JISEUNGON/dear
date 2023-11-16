package com.dearbella.server.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "review")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", columnDefinition = "int")
    private Long reviewId;

    @Column(name = "title", columnDefinition = "text")
    private String title;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    @Column(name = "deleted", columnDefinition = "tinyint")
    private Boolean deleted;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "member_id", columnDefinition = "bigint")
    private Long memberId;

    @Column(name = "hospital_id", columnDefinition = "int")
    private Long hospitalId;

    @Column(name = "hospital_name", length = 100, columnDefinition = "varchar")
    private String hospitalName;

    @Column(name = "doctor_id", columnDefinition = "int")
    private Long doctorId;

    @Column(name = "doctor_name", length = 100, columnDefinition = "varchar")
    private String doctorName;

    @Column(name = "rate", columnDefinition = "float")
    private Float rate;

    @Column(name = "view_num", columnDefinition = "int")
    private Long viewNum;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "review_before_image",
            joinColumns = {@JoinColumn(name = "review_id", referencedColumnName = "review_id")},
            inverseJoinColumns = {@JoinColumn(name = "image_id", referencedColumnName = "image_id")})
    @ApiModelProperty(example = "리뷰 비포 이미지")
    private List<Image> befores;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "review_after_image",
            joinColumns = {@JoinColumn(name = "review_id", referencedColumnName = "review_id")},
            inverseJoinColumns = {@JoinColumn(name = "image_id", referencedColumnName = "image_id")})
    @ApiModelProperty(example = "리뷰 애프터 이미지")
    private List<Image> afters;
}
