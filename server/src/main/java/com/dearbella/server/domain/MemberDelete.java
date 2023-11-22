package com.dearbella.server.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "member_delete")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MemberDelete {
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

    @Column(name = "created_at", columnDefinition = "datetime")
    private LocalDate createdAt;

    @CreationTimestamp
    private LocalDateTime deletedAt;
}
