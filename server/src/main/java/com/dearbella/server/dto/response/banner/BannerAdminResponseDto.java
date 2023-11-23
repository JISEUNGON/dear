package com.dearbella.server.dto.response.banner;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class BannerAdminResponseDto {
    private Long bannerId;
    private String imageUrl;
    private Long sequence;

}
