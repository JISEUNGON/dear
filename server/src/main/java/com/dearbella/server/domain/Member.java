package com.dearbella.server.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Setter
@Table(name = "member")
public class Member {
    @Id
    @Column(name = "member_id", columnDefinition = "bigint")
    private Long memberId;

    @Column(name = "nickname", length = 30, columnDefinition = "varchar")
    private String nickname;

    @Column(name = "login_email", length = 50, columnDefinition = "varchar")
    private String loginEmail;

    @Column(name = "profile_img", columnDefinition = "text")
    private String profileImg;

    @Column(name = "phone", length = 30, columnDefinition = "varchar")
    private String phone;

    @Column(name = "ban", columnDefinition = "tinyint")
    private Boolean ban;

    @CreationTimestamp
    private LocalDate createdAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "member_authority",
            joinColumns = {@JoinColumn(name = "member_id", referencedColumnName = "member_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    @ApiModelProperty(example = "사용자 권한 정보들")
    private List<Authority> authorities;

    public Boolean contain(String role) {
        for(Authority authority: authorities) {
            if(authority.getAuthorityName().equals(role)) {
                return true;
            }
        }

        return false;
    }
}
