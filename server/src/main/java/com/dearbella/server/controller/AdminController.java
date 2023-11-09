package com.dearbella.server.controller;

import com.dearbella.server.domain.Hospital;
import com.dearbella.server.dto.request.hospital.HospitalAddRequestDto;
import com.dearbella.server.service.hospital.HospitalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
@Api(tags = {"관리자 API"})
public class AdminController {
    private final HospitalService hospitalService;

    @ApiOperation("병원 정보 저장")
    @PostMapping(value = "/hospital/add", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Hospital> addHospital(@ModelAttribute HospitalAddRequestDto dto) {
        return ResponseEntity.ok(hospitalService.addHospital(dto));
    }
}
