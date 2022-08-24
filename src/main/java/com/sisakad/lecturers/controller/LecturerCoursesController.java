package com.sisakad.lecturers.controller;

import com.sisakad.lecturers.dto.LecturerCoursesDto;
import com.sisakad.lecturers.dto.LecturersDto;
import com.sisakad.lecturers.services.LecturerCoursesService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
@Slf4j
public class LecturerCoursesController {
    @Autowired
    private LecturerCoursesService lcService;

    @SneakyThrows(Exception.class)
    @PostMapping(path = "/lecturer/courses", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addNewLecturer(@RequestBody List<LecturerCoursesDto> request){
        log.info("api POST /lecturer/courses is hit.");
        return lcService.addLecturerCoursesMapping(request);
    }
}
