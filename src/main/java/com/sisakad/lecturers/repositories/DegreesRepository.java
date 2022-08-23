package com.sisakad.lecturers.repositories;

import com.sisakad.lecturers.models.Degrees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DegreesRepository extends JpaRepository<Degrees, Long> {
    Degrees findDegreesById(Integer degreeId);
    Degrees findDegreesByName(String degreeName);
}
