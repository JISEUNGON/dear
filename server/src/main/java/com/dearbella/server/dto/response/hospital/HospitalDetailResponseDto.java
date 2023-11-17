package com.dearbella.server.dto.response.hospital;

import com.dearbella.server.domain.Image;
import com.dearbella.server.domain.Infra;
import com.dearbella.server.dto.response.doctor.DoctorResponseDto;
import com.dearbella.server.dto.response.review.ReviewResponseDto;
import lombok.*;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class HospitalDetailResponseDto {
    private Long hospitalId;
    private Boolean isMine;
    private List<Image> banners;
    private String hospitalName;
    private String location;
    private Float rate;
    private Long reviewNum;
    private String intro;
    private Set<Infra> infras;
    private List<DoctorResponseDto> doctors;
    private List<Image> befores;
    private List<Image> afters;
    private List<ReviewResponseDto> reviews;
}
