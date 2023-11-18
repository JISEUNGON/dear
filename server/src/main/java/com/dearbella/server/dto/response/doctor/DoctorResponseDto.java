package com.dearbella.server.dto.response.doctor;

import com.dearbella.server.domain.Category;
import com.dearbella.server.domain.Tag;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class DoctorResponseDto {
    private Long doctorId;
    private String doctorImage;
    private String doctorName;
    private String intro;
    private Boolean isMine;
    private List<Category> parts;
    private Float rate;
    private Long reviewNum;
    private String hospitalName;
}
