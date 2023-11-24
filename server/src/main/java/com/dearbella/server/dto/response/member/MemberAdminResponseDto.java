package com.dearbella.server.dto.response.member;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MemberAdminResponseDto {
    private Long memberId;
    private String userIp;
    private String userId;
    private Long totalPage;
}
