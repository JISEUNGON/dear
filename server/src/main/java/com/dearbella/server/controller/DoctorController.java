package com.dearbella.server.controller;

import com.dearbella.server.dto.response.doctor.MyDoctorResponseDto;
import com.dearbella.server.service.doctor.DoctorService;
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
@RequestMapping("/doctor")
@RequiredArgsConstructor
@Slf4j
@Api(tags = {"로그인 후 의사 api"})
public class DoctorController {
    private final DoctorService doctorService;

    @ApiOperation("내가 찜한 의사들")
    @GetMapping("/my")
    public ResponseEntity<List<MyDoctorResponseDto>> getMyDoctors() {
        return ResponseEntity.ok(doctorService.findMyDoctors());
    }
}
