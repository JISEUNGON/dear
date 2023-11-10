package com.dearbella.server.service.doctor;

import com.dearbella.server.domain.Career;
import com.dearbella.server.domain.Doctor;
import com.dearbella.server.domain.IntroLink;
import com.dearbella.server.dto.request.doctor.DoctorAddRequestDto;
import com.dearbella.server.repository.CareerRepository;
import com.dearbella.server.repository.DoctorRepository;
import com.dearbella.server.repository.IntroLinkRepository;
import com.dearbella.server.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final CareerRepository careerRepository;
    private final IntroLinkRepository introLinkRepository;

    @Override
    @Transactional
    public Doctor addDoctor(final DoctorAddRequestDto dto, String image) {
        List<Career> careers = new ArrayList<>();
        List<IntroLink> videos = new ArrayList<>();

        for(int i = 0; i < dto.getCareer().size(); i++) {
            careers.add(
                    careerRepository.save(
                            Career.builder()
                                    .careerName(dto.getCareer().get(i))
                                    .careerDate(LocalDate.parse(dto.getDate().get(i), DateTimeFormatter.ISO_DATE))
                                    .build()
                    )
            );
        }

        for(String video: dto.getVideoLinks()) {
            videos.add(
                    introLinkRepository.save(
                            IntroLink.builder()
                                    .linkUrl(video)
                                    .build()
                    )
            );
        }

        return doctorRepository.save(
                Doctor.builder()
                        .doctorName(dto.getDoctorName())
                        .doctorImage(image)
                        .hospitalName(dto.getHospitalName())
                        .description(dto.getDescription())
                        .tag(dto.getTags())
                        .adminId(JwtUtil.getMemberId())
                        .career(careers)
                        .links(videos)
                        .build()
        );
    }
}
