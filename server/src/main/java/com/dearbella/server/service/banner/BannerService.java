package com.dearbella.server.service.banner;

import com.dearbella.server.domain.Banner;
import com.dearbella.server.dto.request.banner.BannerAddRequestDto;
import com.dearbella.server.dto.request.banner.BannerEditRequestDto;
import com.dearbella.server.dto.response.banner.BannerAdminResponseDto;
import com.dearbella.server.dto.response.banner.BannerDetailResponseDto;
import com.dearbella.server.dto.response.banner.BannerResponseDto;

import java.util.List;

public interface BannerService {
    public Banner addBanner(BannerAddRequestDto dto, List<String> mainImages, List<String> detailImages);

    public List<BannerResponseDto> getBanners(Boolean location);
    public BannerDetailResponseDto findById(Long bannerId);
    public List<BannerAdminResponseDto> getBanners(Long location, Long page);
    public Banner editBanner(BannerEditRequestDto dto, List<String> mainImages, List<String> detailImages);

    public Banner getBanner(Long bannerId);
}
