package com.sisakad.lecturers.services;

import com.sisakad.lecturers.dto.LecturerCoursesDto;
import com.sisakad.lecturers.models.Courses;
import com.sisakad.lecturers.models.LecturerCourses;
import com.sisakad.lecturers.models.Lecturers;
import com.sisakad.lecturers.repositories.LecturerCoursesRepository;
import com.sisakad.lecturers.repositories.LecturersRepository;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackOn = Exception.class)
public class LecturerCoursesService {
    @Autowired
    private LecturerCoursesRepository lecturerCoursesRepository;

    @SneakyThrows(Exception.class)
    @ApiOperation("Add new mapping lecturer x courses")
    public ResponseEntity<Object> addLecturerCoursesMapping(List<LecturerCoursesDto> request){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        for(LecturerCoursesDto item: request){
            if(item.getLecturerId() == null || item.getLecturerId() == 0){
                Map<String, Object> errResp = new HashMap<String, Object>();
                errResp.put("error_message", "Field lecturerId is mandatory and its value cannot be 0");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(errResp);
            }
            if(item.getCourseId() == null || item.getCourseId() == 0){
                Map<String, Object> errResp = new HashMap<String, Object>();
                errResp.put("error_message", "Field courseId is mandatory and its value cannot be 0");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(errResp);
            }

            List<LecturerCourses> findData = lecturerCoursesRepository.findLecturerCoursesByLecturersId(item.getLecturerId());
            if(findData != null && findData.size() > 0){
                for(LecturerCourses existData: findData){
                    lecturerCoursesRepository.delete(existData);
                }
            }
        }

        for(LecturerCoursesDto item: request){
            LecturerCourses data = new LecturerCourses();
            Courses crs = new Courses();
            crs.setId(item.getCourseId());
            data.setCourses(crs);
            Lecturers lct = new Lecturers();
            lct.setId(item.getLecturerId());
            data.setLecturers(lct);
            data.setStartedAt(item.getStartedAt());
            data.setEndedAt(item.getEndedAt());
            LocalDateTime todayDateTime = LocalDateTime.now();
            data.setCreatedAt(todayDateTime);

            lecturerCoursesRepository.save(data);
        }

        return  ResponseEntity.status(HttpStatus.OK).headers(headers).body(request);
    }
}
