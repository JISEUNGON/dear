package com.dearbella.server.service.banner;

import com.dearbella.server.domain.Banner;
import com.dearbella.server.domain.Image;
import com.dearbella.server.domain.Infra;
import com.dearbella.server.dto.request.banner.BannerAddRequestDto;
import com.dearbella.server.enums.hospital.InfraEnum;
import com.dearbella.server.exception.banner.BannerInfraNotFoundException;
import com.dearbella.server.repository.BannerRepository;
import com.dearbella.server.repository.ImageRepository;
import com.dearbella.server.repository.InfraRepository;
import com.dearbella.server.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {
    private final BannerRepository bannerRepository;
    private final InfraRepository infraRepository;
    private final ImageRepository imageRepository;

    @Override
    @Transactional
    public Banner addBanner(final BannerAddRequestDto dto, final List<String> mainImages, final List<String> detailImages) {
        List<Image> mains = new ArrayList<>();
        List<Image> details = new ArrayList<>();
        Long memberId = JwtUtil.getMemberId();
        List<Infra> infras = new ArrayList<>();

        for(int i = 1; i < mainImages.size(); i++) {
            mains.add(
              imageRepository.save(
                      Image.builder()
                          .imageUrl(mainImages.get(i))
                          .memberId(memberId)
                          .build()
              )
            );
        }

        for(String image: detailImages) {
            details.add(
                    imageRepository.save(
                        Image.builder()
                                .imageUrl(image)
                                .memberId(memberId)
                                .build()
                    )
            );
        }

        for(Long tag: dto.getTags()) {
            infras.add(
                    infraRepository.findById(tag).orElseThrow(
                            () -> new BannerInfraNotFoundException(tag)
                    )
            );
        }

        return bannerRepository.save(
                Banner.builder()
                        .bannerLink(dto.getBannerLink())
                        .bannerImages(mains)
                        .bannerLocation(dto.getIsTop())
                        .hospitalName(dto.getHospitalName())
                        .mainImage(mainImages.get(0))
                        .hospitalLocation(dto.getHospitalLocation())
                        .description(dto.getDescription())
                        .adminId(memberId)
                        .bannerImages(mains)
                        .bannerDetailImages(details)
                        .bannerInfra(infras)
                        .build()
        );
    }
}
