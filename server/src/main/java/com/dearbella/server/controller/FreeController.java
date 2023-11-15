package com.dearbella.server.controller;

import com.dearbella.server.domain.Banner;
import com.dearbella.server.dto.response.banner.BannerDetailResponseDto;
import com.dearbella.server.dto.response.banner.BannerResponseDto;
import com.dearbella.server.service.banner.BannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/free")
@RequiredArgsConstructor
@Slf4j
@Api(tags = {"권한 필요 없는 API"})
public class FreeController {
    private final BannerService bannerService;

    @ApiOperation("배너 조회")
    @GetMapping("/banner")
    public ResponseEntity<List<BannerResponseDto>> getBanners(@RequestParam Long location) {
        return ResponseEntity.ok(bannerService.getBanners(location == 1));
    }

    @ApiOperation("배너 상세 조회")
    @GetMapping("/banner/info")
    public ResponseEntity<BannerDetailResponseDto> getBanner(@RequestParam Long bannerId) {
        return ResponseEntity.ok(bannerService.findById(bannerId));
    }
}
