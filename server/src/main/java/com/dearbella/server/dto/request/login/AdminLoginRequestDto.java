package com.dearbella.server.dto.request.login;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AdminLoginRequestDto {
    private String userId;
    private String password;
}
