package com.dearbella.server.service.hospital;

import com.dearbella.server.domain.Hospital;
import com.dearbella.server.dto.request.hospital.HospitalAddRequestDto;

import java.util.List;

public interface HospitalService {
    public Hospital addHospital(HospitalAddRequestDto dto, List<String> befores, List<String> afters);
}
