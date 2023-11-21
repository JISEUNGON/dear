package com.dearbella.server.controller;

import com.dearbella.server.domain.Inquiry;
import com.dearbella.server.dto.request.inquiry.InquiryAddRequestDto;
import com.dearbella.server.dto.response.inquiry.InquiryResponseDto;
import com.dearbella.server.service.inquiry.InquiryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inquiry")
@Slf4j
@RequiredArgsConstructor
@Api(tags = {"문의하기 API"})
public class InquiryController {
    private final InquiryService inquiryService;

    @PostMapping
    @ApiOperation("문의하기")
    public ResponseEntity<Inquiry> addInquiry(@RequestBody InquiryAddRequestDto dto) {
        return ResponseEntity.ok(inquiryService.addInquiry(dto));
    }

    @ApiOperation("내 문의 목록들")
    @GetMapping("/my-list")
    public ResponseEntity<List<InquiryResponseDto>> getMyInquiries() {
        return ResponseEntity.ok(inquiryService.findMyInquiries());
    }
}
