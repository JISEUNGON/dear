package com.dearbella.server.dto.request.hospital;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class HospitalEditRequestDto {
    private Long hospitalId;
    private String link;
    private List<MultipartFile> hospitalImages;
    private String name;
    private String location;
    private String description;
    private List<Long> infras;
    private List<MultipartFile> befores;
    private List<MultipartFile> afters;
    private List<MultipartFile> banners;
    private Long sequence;
    private Long anesthesiologist;
    private Long plasticSurgeon;
    private Long dermatologist;
}
