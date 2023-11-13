package com.dearbella.server.dto.request.hospital;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HospitalAddRequestDto {
    private String link;
    private List<MultipartFile> hospitalImages;
    private String name;
    private String location;
    private String description;
    private List<Long> infras;
    private List<MultipartFile> befores;
    private List<MultipartFile> afters;
    private Long sequence;
    private Long anesthesiologist;
    private Long plasticSurgeon;
    private Long dermatologist;
}
