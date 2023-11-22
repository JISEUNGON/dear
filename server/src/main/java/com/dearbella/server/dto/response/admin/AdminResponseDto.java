package com.dearbella.server.dto.response.admin;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AdminResponseDto {
    private Long memberId;
    private String hospitalName;
    private String adminId;
}
