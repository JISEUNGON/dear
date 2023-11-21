package com.dearbella.server.service.hospital;

import com.dearbella.server.domain.Hospital;
import com.dearbella.server.domain.HospitalMember;
import com.dearbella.server.dto.request.hospital.HospitalAddRequestDto;
import com.dearbella.server.dto.response.hospital.HospitalDetailResponseDto;
import com.dearbella.server.dto.response.hospital.HospitalResponseDto;
import com.dearbella.server.dto.response.hospital.MyHospitalResponseDto;

import java.util.List;
import java.util.Set;

public interface HospitalService {
    public Hospital addHospital(HospitalAddRequestDto dto, List<String> befores, List<String> afters, List<String> banners);
    public List<HospitalResponseDto> getAll(Long category, Long sort);
    public HospitalDetailResponseDto findById(Long id);
    public Set<HospitalResponseDto> findByQuery(String query);

    public List<MyHospitalResponseDto> findByMemberId();

    public HospitalMember addWishList(Long hospitalId);

    public void deleteWish(Long hospitalId);
}
