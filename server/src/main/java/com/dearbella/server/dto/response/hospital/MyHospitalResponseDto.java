package com.dearbella.server.dto.response.hospital;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MyHospitalResponseDto {
    private Long hospitalId;
    private String hospitalName;
    private Float rate;
    private Long reviewNum;
}
