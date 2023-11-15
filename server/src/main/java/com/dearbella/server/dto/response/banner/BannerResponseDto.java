package com.dearbella.server.dto.response.banner;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BannerResponseDto {
    private Long bannerId;
    private String bannerLink;
    private String hospitalName;
    private String mainImage;
}
