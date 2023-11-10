package com.dearbella.server.service.doctor;

import com.dearbella.server.domain.Doctor;
import com.dearbella.server.dto.request.doctor.DoctorAddRequestDto;
import com.dearbella.server.dto.request.doctor.DoctorFindRequestDto;
import com.dearbella.server.dto.response.doctor.DoctorFindResponseDto;

import java.util.List;

public interface DoctorService {
    public Doctor addDoctor(DoctorAddRequestDto dto, String image);
    public List<DoctorFindResponseDto> findDoctors(DoctorFindRequestDto dto);
}
