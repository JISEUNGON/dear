package com.dearbella.server.service.banner;

import com.dearbella.server.domain.Banner;
import com.dearbella.server.dto.request.banner.BannerAddRequestDto;
import com.dearbella.server.dto.response.banner.BannerDetailResponseDto;
import com.dearbella.server.dto.response.banner.BannerResponseDto;

import java.util.List;

public interface BannerService {
    Banner addBanner(BannerAddRequestDto dto, List<String> mainImages, List<String> detailImages);

    public List<BannerResponseDto> getBanners(Boolean location);

    public BannerDetailResponseDto findById(Long bannerId);
}
