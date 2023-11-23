package com.dearbella.server.dto.response.doctor;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class DoctorAdminResponseDto {
    private Long doctorId;
    private String doctorName;
    private Long totalPage;
}
