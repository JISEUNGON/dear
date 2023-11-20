package com.dearbella.server.controller;

import com.dearbella.server.dto.response.hospital.MyHospitalResponseDto;
import com.dearbella.server.service.hospital.HospitalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hospital")
@RequiredArgsConstructor
@Slf4j
@Api(tags = {"로그인한 사용자에 대한 병원 API"})
public class HospitalController {
    private final HospitalService hospitalService;

    @ApiOperation("내가 찜한 병원 리스트")
    @GetMapping("/my")
    public ResponseEntity<List<MyHospitalResponseDto>> getMyHospitals() {
        return ResponseEntity.ok(hospitalService.findByMemberId());
    }
}
