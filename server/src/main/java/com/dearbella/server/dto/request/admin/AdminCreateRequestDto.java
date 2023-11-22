package com.dearbella.server.dto.request.admin;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AdminCreateRequestDto {
    private Long hospitalId;
    private String hospitalName;
    private String userId;
    private String password;
}
