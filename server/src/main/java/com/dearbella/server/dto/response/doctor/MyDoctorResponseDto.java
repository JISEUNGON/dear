package com.dearbella.server.dto.response.doctor;

import com.dearbella.server.domain.Category;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MyDoctorResponseDto {
    private Long doctorId;
    private String doctorName;
    private String DoctorImage;
    private String hospitalName;
    private Long reviewNum;
    private Float rate;
    private List<Category> categories;
    private String intro;
}
