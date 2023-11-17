package com.dearbella.server.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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

    @Column(name = "sequence", columnDefinition = "int")
    private Long sequence;

    @Column(name = "total_rate", columnDefinition = "float")
    private Float totalRate;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "doctor_category",
            joinColumns = {@JoinColumn(name = "doctor_id", referencedColumnName = "doctor_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_num", referencedColumnName = "category_num")})
    @ApiModelProperty(example = "전문 분야")
    private List<Category> categories;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "doctor_career",
            joinColumns = {@JoinColumn(name = "doctor_id", referencedColumnName = "doctor_id")},
            inverseJoinColumns = {@JoinColumn(name = "career_id", referencedColumnName = "career_id")})
    @ApiModelProperty(example = "경력")
    private List<Career> career;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "doctor_link",
            joinColumns = {@JoinColumn(name = "doctor_id", referencedColumnName = "doctor_id")},
            inverseJoinColumns = {@JoinColumn(name = "link_id", referencedColumnName = "link_id")})
    @ApiModelProperty(example = "영상 링크 주")
    private List<IntroLink> links;

    @Column(name = "admin_id", columnDefinition = "bigint")
    private Long adminId;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Boolean contains(Long category) {
        for(Category category1 : categories) {
            if(category1.getCategoryNum() == category) {
                return true;
            }
        }

        return false;
    }
}
