package com.dearbella.server.service.hospital;

import com.dearbella.server.domain.Hospital;
import com.dearbella.server.dto.request.hospital.HospitalAddRequestDto;

public interface HospitalService {
    public Hospital addHospital(HospitalAddRequestDto dto);
}
