package com.dearbella.server.service.banner;

import com.dearbella.server.domain.Banner;
import com.dearbella.server.dto.request.banner.BannerAddRequestDto;

import java.util.List;

public interface BannerService {
    Banner addBanner(BannerAddRequestDto dto, List<String> mainImages, List<String> detailImages);
}
