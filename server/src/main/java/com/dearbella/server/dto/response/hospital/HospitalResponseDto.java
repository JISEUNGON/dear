package com.dearbella.server.dto.response.hospital;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class HospitalResponseDto {
    private Long hospitalId;
    private String hospitalName;
    private String location;
    private Float rate;
    private Long reviewNum;
    private Boolean isMine;
}
