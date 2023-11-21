package com.dearbella.server.service.doctor;

import com.dearbella.server.domain.Doctor;
import com.dearbella.server.dto.request.doctor.DoctorAddRequestDto;
import com.dearbella.server.dto.request.doctor.DoctorDetailResponseDto;
import com.dearbella.server.dto.response.doctor.DoctorResponseDto;
import com.dearbella.server.dto.response.doctor.MyDoctorResponseDto;

import java.util.List;
import java.util.Set;

public interface DoctorService {
    public Doctor addDoctor(DoctorAddRequestDto dto, String image);
    public List<DoctorResponseDto> findAll(Long category, Long sort);
    public DoctorDetailResponseDto findById(Long doctorId);
    public Set<DoctorResponseDto> findByQuery(String query);

    public List<MyDoctorResponseDto> findMyDoctors();
}
