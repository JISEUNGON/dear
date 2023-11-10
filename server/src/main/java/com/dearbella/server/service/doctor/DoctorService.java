package com.dearbella.server.service.doctor;

import com.dearbella.server.domain.Doctor;
import com.dearbella.server.dto.request.doctor.DoctorAddRequestDto;

import java.util.List;

public interface DoctorService {
    public Doctor addDoctor(DoctorAddRequestDto dto, String image);
}
