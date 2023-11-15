package com.dearbella.server.dto.response.banner;

import com.dearbella.server.domain.Image;
import com.dearbella.server.domain.Infra;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BannerDetailResponseDto {
    private Long bannerId;
    private String bannerLink;
    private String hospitalName;
    private String mainImage;
    private String hospitalLocation;
    private String description;
    private List<Image> bannerImages;
    private List<Image> bannerDetailImages;
    private List<Infra> bannerInfra;
}
