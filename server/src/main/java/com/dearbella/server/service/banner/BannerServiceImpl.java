package com.dearbella.server.service.banner;

import com.dearbella.server.domain.Banner;
import com.dearbella.server.domain.Image;
import com.dearbella.server.domain.Infra;
import com.dearbella.server.domain.Review;
import com.dearbella.server.dto.request.banner.BannerAddRequestDto;
import com.dearbella.server.dto.request.banner.BannerEditRequestDto;
import com.dearbella.server.dto.response.banner.BannerAdminResponseDto;
import com.dearbella.server.dto.response.banner.BannerDetailResponseDto;
import com.dearbella.server.dto.response.banner.BannerResponseDto;
import com.dearbella.server.exception.banner.BannerIdNotFoundException;
import com.dearbella.server.exception.banner.BannerInfraNotFoundException;
import com.dearbella.server.exception.banner.BannerNotExistException;
import com.dearbella.server.exception.hospital.HospitalIdNotFoundException;
import com.dearbella.server.exception.hospital.HospitalNameNotFoundException;
import com.dearbella.server.repository.*;
import com.dearbella.server.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.dearbella.server.config.MapperConfig.modelMapper;

@Slf4j
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {
    private final BannerRepository bannerRepository;
    private final InfraRepository infraRepository;
    private final ImageRepository imageRepository;
    private final ReviewRepository reviewRepository;
    private final HospitalRepository hospitalRepository;

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
                        .sequence(dto.getSequence())
                        .build()
        );
    }

    @Override
    @Transactional
    public List<BannerResponseDto> getBanners(Boolean location) {
        List<BannerResponseDto> response = new ArrayList<>();

        List<Banner> banners = bannerRepository.findBannerByBannerLocation(location, Sort.by(Sort.Direction.ASC, "sequence"));

        if(banners.isEmpty()) {
            throw new BannerNotExistException();
        }
        else
        {
            for(Banner banner: banners) {
                response.add(
                        modelMapper.map(banner, BannerResponseDto.class)
                );
            }
        }

        return response;
    }

    @Override
    @Transactional
    public BannerDetailResponseDto findById(Long bannerId) {
        Banner banner = bannerRepository.findById(bannerId).orElseThrow(() -> new BannerIdNotFoundException(bannerId));

        final List<Review> byHospitalName = reviewRepository.findByHospitalName(banner.getHospitalName());

        BannerDetailResponseDto map = modelMapper.map(banner, BannerDetailResponseDto.class);

        map.setReviewNum(Long.valueOf(byHospitalName.size()));

        map.setRate(hospitalRepository.findByHospitalName(banner.getHospitalName()).orElseThrow(
                () -> new HospitalNameNotFoundException(banner.getHospitalName())
        ).getTotalRate());

        return map;
    }

    @Override
    @Transactional
    public List<BannerAdminResponseDto> getBanners(final Long location, final Long page) {
        Page<Banner> banners = bannerRepository.findAll(PageRequest.of(page.intValue(), 3, Sort.by(Sort.Direction.ASC, "sequence")));

        List<BannerAdminResponseDto> responseDtos = new ArrayList<>();

        for(Banner banner: banners) {
            responseDtos.add(
                    BannerAdminResponseDto.builder()
                            .bannerId(banner.getBannerId())
                            .sequence(banner.getSequence())
                            .imageUrl(banner.getMainImage())
                            .build()
            );
        }
        return responseDtos;
    }

    @Override
    @Transactional
    public Banner editBanner(final BannerEditRequestDto dto, final List<String> mainImages, final List<String> detailImages) {
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

        Banner banner = bannerRepository.findById(dto.getBannerId()).orElseThrow(
                () -> new BannerIdNotFoundException(dto.getBannerId())
        );

        banner.setBannerImages(mains);
        banner.setBannerLocation(dto.getIsTop());
        banner.setHospitalName(dto.getHospitalName());
        banner.setMainImage(mainImages.get(0));
        banner.setHospitalLocation(dto.getHospitalLocation());
        banner.setDescription(dto.getDescription());
        banner.setAdminId(memberId);
        banner.setBannerImages(mains);
        banner.setBannerDetailImages(details);
        banner.setBannerInfra(infras);
        banner.setSequence(dto.getSequence());

        return bannerRepository.save(banner);
    }

    @Override
    @Transactional
    public Banner getBanner(final Long bannerId) {
        return bannerRepository.findById(bannerId).orElseThrow(
                () ->new BannerIdNotFoundException(bannerId)
        );
    }
}
