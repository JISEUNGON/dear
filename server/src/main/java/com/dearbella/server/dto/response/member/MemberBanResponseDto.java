package com.dearbella.server.dto.response.member;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MemberBanResponseDto {
    private Long memberId;
    private String userId;
    private Boolean isBan;
    private Long totalPage;
}
