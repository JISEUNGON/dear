package com.dearbella.server.service.doctor;

import com.dearbella.server.domain.Doctor;
import com.dearbella.server.dto.request.doctor.DoctorAddRequestDto;

public interface DoctorService {
    public Doctor addDoctor(DoctorAddRequestDto dto, String image);
}
