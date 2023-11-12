package com.dearbella.server.dto.request.banner;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BannerAddRequestDto {
    private Boolean isTop;
    private String bannerLink;
    private List<MultipartFile> bannerImages;
    private String hospitalName;
    private String hospitalLocation;
    private String description;
    private List<Long> tags;
    private List<MultipartFile> detailImages;
}
