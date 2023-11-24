package com.dearbella.server.dto.response.hospital;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class HospitalAdminResponseDto {
    private Long hospitalId;
    private String hospitalName;
    private Long totalPage;
}
