package com.sisakad.lecturers.controller;

import com.sisakad.lecturers.dto.LecturersDto;
import com.sisakad.lecturers.services.LecturersService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(value = "/api/v1")
@Slf4j
public class LecturersController {
    @Autowired
    private LecturersService lecturersService;

    @SneakyThrows(Exception.class)
    @PostMapping(path = "/lecturer", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addNewLecturer(@RequestBody LecturersDto request){
        log.info("api POST /lecturer is hit.");
        return lecturersService.addLecturer(request);
    }

    @SneakyThrows(Exception.class)
    @GetMapping(path = "/lecturer/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getLecturer(@PathVariable Integer id){
        log.info("api GET /lecturer/{id} is hit.");
        return lecturersService.getLecturerById(id);
    }

    @SneakyThrows(Exception.class)
    @GetMapping(path = "/lecturer", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getLecturerData(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Date dob,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String gender
    ){
        log.info("api GET /lecturer?name={name}&dob={dob}&address={address}&gender={gender} is hit.");
        return lecturersService.getLecturerByData(name, dob, address, gender);
    }

    @SneakyThrows(Exception.class)
    @PutMapping(path = "/lecturer/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getLecturerData(
            @PathVariable Integer id,
            @RequestBody LecturersDto request
    ){
        log.info("api PUT /lecturer/{id} is hit.");
        return lecturersService.updateLecturerData(id, request);
    }

    @SneakyThrows(Exception.class)
    @DeleteMapping(path = "/lecturer/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getLecturerData(@PathVariable Integer id){
        log.info("api PUT /lecturer/{id} is hit.");
        return lecturersService.deleteLecturerData(id);
    }
}
