package com.dearbella.server.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "post")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class
Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", columnDefinition = "int")
    private Long postId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinTable(
            name = "post_tag",
            joinColumns = {@JoinColumn(name = "post_id", referencedColumnName = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id", referencedColumnName = "tag_id")})
    @ApiModelProperty(example = "게시물 형태")
    private Tag tag;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "post_image",
            joinColumns = {@JoinColumn(name = "post_id", referencedColumnName = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "image_id", referencedColumnName = "image_id")})
    @ApiModelProperty(example = "게시물 이미지")
    private List<Image> postImages;

    @Column(name = "title", columnDefinition = "text")
    private String title;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    @Column(name = "member_id", columnDefinition = "bigint")
    private Long memberId;

    @Column(name = "view_num", columnDefinition = "int")
    private Long viewNum;

    @Column(name = "deleted", columnDefinition = "tinyint")
    private Boolean deleted;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
