package com.sisakad.lecturers.repositories;

import com.sisakad.lecturers.models.LecturerCourses;
import com.sisakad.lecturers.models.Lecturers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LecturerCoursesRepository extends JpaRepository<LecturerCourses, Long> {
    LecturerCourses findLecturerCoursesById (Integer id);
    List<LecturerCourses> findLecturerCoursesByLecturersId(Integer lecturerId);
    List<LecturerCourses> findLecturerCoursesByCoursesId(Integer courseId);
}
