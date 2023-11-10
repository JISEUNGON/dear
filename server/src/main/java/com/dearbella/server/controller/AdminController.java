package com.dearbella.server.controller;

import com.dearbella.server.domain.Doctor;
import com.dearbella.server.domain.Hospital;
import com.dearbella.server.dto.request.doctor.DoctorAddRequestDto;
import com.dearbella.server.dto.request.hospital.HospitalAddRequestDto;
import com.dearbella.server.service.doctor.DoctorService;
import com.dearbella.server.service.doctor.DoctorServiceImpl;
import com.dearbella.server.service.hospital.HospitalService;
import com.dearbella.server.service.s3.S3UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
@Api(tags = {"관리자 API"})
public class AdminController {
    private final HospitalService hospitalService;
    private final S3UploadService s3UploadService;
    private final DoctorService doctorService;

    @ApiOperation("병원 정보 넣기")
    @PostMapping(value = "/hospital/save", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Transactional
    public ResponseEntity<Hospital> saveHospital(@ModelAttribute HospitalAddRequestDto dto) throws IOException {
        List<String> befores = new ArrayList<>();
        List<String> afters = new ArrayList<>();

        for(MultipartFile before: dto.getBefores()) {
            befores.add(
                    s3UploadService.upload(before, "/dearbella/hospital/before", false)
            );
        }

        for(MultipartFile after: dto.getAfters()) {
            afters.add(
                    s3UploadService.upload(after, "/dearbella/hospital/after", false)
            );
        }

        return ResponseEntity.ok(hospitalService.addHospital(dto, befores, afters));
    }

    @ApiOperation("의사 정보 넣기")
    @PostMapping(value = "/doctor/save", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Transactional
    public ResponseEntity<Doctor> saveDoctor(@ModelAttribute DoctorAddRequestDto dto) throws IOException {
        final String upload = s3UploadService.upload(dto.getImage(), "/dearbella/doctor/", false);

        return ResponseEntity.ok(doctorService.addDoctor(dto, upload));
    }
}
