package com.dearbella.server.controller;

import com.dearbella.server.domain.Authority;
import com.dearbella.server.service.member.MemberService;
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
@RequestMapping("/authority")
@RequiredArgsConstructor
@Slf4j
@Api(tags = {"권한 API"})
public class AuthorityController {
    private final MemberService memberService;

    @ApiOperation("권한 정보 확인")
    @GetMapping
    public ResponseEntity<List<Authority>> getAuthorities() {
        return ResponseEntity.ok(memberService.findById().getAuthorities());
    }
}
