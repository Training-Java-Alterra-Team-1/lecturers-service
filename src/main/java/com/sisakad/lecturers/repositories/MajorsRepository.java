package com.sisakad.lecturers.repositories;

import com.sisakad.lecturers.models.Majors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MajorsRepository extends JpaRepository<Majors, Long> {
    Majors findMajorsById(Integer majorId);
    Majors findMajorsByName(String majorName);
}
