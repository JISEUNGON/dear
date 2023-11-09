package com.dearbella.server.service.hospital;

import com.dearbella.server.domain.Hospital;
import com.dearbella.server.dto.request.hospital.HospitalAddRequestDto;
import com.dearbella.server.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {
    private final HospitalRepository hospitalRepository;

    @Override
    public Hospital addHospital(final HospitalAddRequestDto dto) {
        return null;
    }
}
