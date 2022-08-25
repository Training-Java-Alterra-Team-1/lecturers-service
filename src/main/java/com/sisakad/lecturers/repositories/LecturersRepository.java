package com.sisakad.lecturers.repositories;

import com.sisakad.lecturers.models.Lecturers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface LecturersRepository extends JpaRepository<Lecturers, Long> {
    Lecturers findLecturersById(Integer lecturerId);
    List<Lecturers> findLecturersByNameContainingIgnoreCaseOrDobOrGenderOrAddressContainingIgnoreCase(String lecturerName, Date dob, String gender, String address);
}
