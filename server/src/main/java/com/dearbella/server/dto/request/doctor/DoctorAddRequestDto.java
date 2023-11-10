package com.dearbella.server.dto.request.doctor;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DoctorAddRequestDto {
    private MultipartFile image;
    private String doctorName;
    private String hospitalName;
    private String description;
    private List<String> videoLinks;
    private List<String> date;
    private List<String> career;
    private List<Long> tags;
    private Long sequence;
}
