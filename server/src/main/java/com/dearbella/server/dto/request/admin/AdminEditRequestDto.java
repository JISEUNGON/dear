package com.dearbella.server.dto.request.admin;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AdminEditRequestDto {
    private Long memberId;
    private String userId;
    private String password;
}
