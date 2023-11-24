package com.dearbella.server.repository;

import com.dearbella.server.domain.Category;
import com.dearbella.server.domain.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.print.Doc;
import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    public List<Doctor> findByHospitalName(String hospitalName);
    public List<Doctor> findByHospitalNameContainingAndDeletedFalse(String hospitalName);
    public List<Doctor> findByDescriptionAndDeletedFalse(String query);
    public List<Doctor> findByCategoriesAndDeletedFalse(Category category);
    public List<Doctor> findByCategoriesAndDeletedFalse(Category category, Sort sort);
    public Page<Doctor> findByDeletedFalse(PageRequest doctorName);
    public Optional<Doctor> findByDoctorIdAndDeletedFalse(Long doctorId);
    public List<Doctor> findByAndDeletedFalse(Sort sort1);
}
