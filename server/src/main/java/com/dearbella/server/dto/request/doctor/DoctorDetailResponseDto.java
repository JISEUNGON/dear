package com.dearbella.server.dto.request.doctor;

import com.dearbella.server.domain.Career;
import com.dearbella.server.domain.Category;
import com.dearbella.server.domain.IntroLink;
import com.dearbella.server.dto.response.review.ReviewPreviewResponseDto;
import com.dearbella.server.dto.response.review.ReviewResponseDto;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class DoctorDetailResponseDto {
    private Long doctorId;
    private String doctorName;
    private String doctorImage;
    private String description;
    private String hospitalName;
    private Float rate;
    private Long reviewNum;
    private Boolean isMine;
    private List<Category> parts;
    private List<Career> careers;
    private List<IntroLink> videos;
    private List<ReviewPreviewResponseDto> reviews;
}
