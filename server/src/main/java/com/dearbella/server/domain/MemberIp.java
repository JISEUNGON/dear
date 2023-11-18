package com.dearbella.server.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "member_ip")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MemberIp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_ip_id", columnDefinition = "int")
    private Long memberIpId;

    @Column(name = "member_id", columnDefinition = "bigint")
    private Long memberId;

    @Column(name = "ip", length = 20, columnDefinition = "varchar")
    private String ip;
}
