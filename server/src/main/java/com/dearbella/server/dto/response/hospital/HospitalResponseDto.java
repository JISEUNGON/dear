package com.dearbella.server.dto.response.hospital;

import com.dearbella.server.dto.response.review.ReviewResponseDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class HospitalResponseDto {
    private Long hospitalId;
    private String hospitalImage;
    private String hospitalName;
    private String location;
    private Float rate;
    private Long reviewNum;
    private Boolean isMine;

    @Override
    public int hashCode() {
        return this.hospitalId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof HospitalResponseDto) {
            HospitalResponseDto reviewResponseDto = (HospitalResponseDto) obj;

            return this.hashCode() == reviewResponseDto.hashCode();
        }

        return false;
    }
}
