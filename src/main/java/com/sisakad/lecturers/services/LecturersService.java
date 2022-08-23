package com.sisakad.lecturers.services;

import com.sisakad.lecturers.dto.LecturersDto;
import com.sisakad.lecturers.models.Lecturers;
import com.sisakad.lecturers.models.Majors;
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
import java.util.*;

@Service
@Transactional(rollbackOn = Exception.class)
public class LecturersService {
    @Autowired
    private LecturersRepository lecturersRepository;

    @SneakyThrows(Exception.class)
    @ApiOperation("Add new lecturer")
    public ResponseEntity<Object> addLecturer(LecturersDto request){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if(request.getMajorId() == null || request.getMajorId() == 0){
            Map<String, Object> errResp = new HashMap<String, Object>();
            errResp.put("error_message", "Field majorId is mandatory and its value cannot be 0");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(errResp);
        }
        if(request.getLecturerName() == null || request.getLecturerName() == ""){
            Map<String, Object> errResp = new HashMap<String, Object>();
            errResp.put("error_message", "Field lecturerName is mandatory");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(errResp);
        }
        if(request.getDob() == null){
            Map<String, Object> errResp = new HashMap<String, Object>();
            errResp.put("error_message", "Field dob is mandatory");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(errResp);
        }
        if(request.getGender() == null || request.getGender() == ""){
            Map<String, Object> errResp = new HashMap<String, Object>();
            errResp.put("error_message", "Field gender is mandatory");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(errResp);
        }

        Lecturers lecturer = new Lecturers();
        lecturer.setAddress(request.getAddress());
        lecturer.setDob(request.getDob());
        lecturer.setGender(request.getGender());
        Majors major = new Majors();
        major.setId(request.getMajorId());
        lecturer.setMajors(major);
        LocalDateTime todayDateTime = LocalDateTime.now();
        lecturer.setCreatedAt(todayDateTime);

        lecturersRepository.save(lecturer);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(request);
    }

    @SneakyThrows(Exception.class)
    @ApiOperation("Get all lecturers")
    public ResponseEntity<Object> getAllLecturers(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        List<Lecturers> lecturers = lecturersRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(lecturers);
    }

    @SneakyThrows(Exception.class)
    @ApiOperation("Get lecturer by Id")
    public ResponseEntity<Object> getLecturerById(Integer lecturerId){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if(lecturerId == null || lecturerId == 0){
            Map<String, Object> errResp = new HashMap<String, Object>();
            errResp.put("error_message", "LecturerId is mandatory and its value cannot be 0");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(errResp);
        }

        Lecturers lecturers = lecturersRepository.findLecturersById(lecturerId);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(lecturers);
    }

    @SneakyThrows(Exception.class)
    @ApiOperation("Get lecturer by Name, DoB, Address, Gender")
    public ResponseEntity<Object> getLecturerByData(String name, Date dob, String address, String gender){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        List<Lecturers> resp = new ArrayList<Lecturers>();

        if(
                (name == null || name == "") &&
                        (dob == null) &&
                        (address == null || address == "") &&
                        (gender == null || gender == "")
        ){
            resp = lecturersRepository.findAll();
        }else{
            resp = lecturersRepository.findLecturersByNameContainingIgnoreCaseOrDobOrGenderOrAddressContainingIgnoreCase(name, dob, gender, address);
        }

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(resp);
    }

    @SneakyThrows(Exception.class)
    @ApiOperation("Update lecturer data by id")
    public ResponseEntity<Object> updateLecturerData(Integer lecturerId, LecturersDto request){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if(lecturerId == null || lecturerId == 0){
            Map<String, Object> errResp = new HashMap<String, Object>();
            errResp.put("error_message", "LecturerId is mandatory and its value cannot be 0");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(errResp);
        }

        Lecturers updatedData = lecturersRepository.findLecturersById(lecturerId);
        if(updatedData == null){
            Map<String, Object> errResp = new HashMap<String, Object>();
            errResp.put("error_message", "Lecturer data by id " + lecturerId + " not found.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(errResp);
        }else{
            if(request.getMajorId() == 0){
                Map<String, Object> errResp = new HashMap<String, Object>();
                errResp.put("error_message", "Field majorId value cannot be 0");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(errResp);
            }
            if(request.getLecturerName() != null && request.getLecturerName() != ""){
                updatedData.setName(request.getLecturerName());
            }
            if(request.getDob() != null){
                updatedData.setDob(request.getDob());
            }
            if(request.getGender() != null || request.getGender() != ""){
                updatedData.setGender(request.getGender());
            }
            if(request.getAddress() != null || request.getAddress() != ""){
                updatedData.setAddress(request.getAddress());
            }
        }

        LocalDateTime todayDateTime = LocalDateTime.now();
        updatedData.setUpdatedAt(todayDateTime);
        lecturersRepository.save(updatedData);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(request);
    }

    @SneakyThrows(Exception.class)
    @ApiOperation("Delete lecturer data by id")
    public ResponseEntity<Object> deleteLecturerData(Integer lecturerId){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if(lecturerId == null || lecturerId == 0){
            Map<String, Object> errResp = new HashMap<String, Object>();
            errResp.put("error_message", "LecturerId is mandatory and its value cannot be 0");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(errResp);
        }

        Lecturers data = lecturersRepository.findLecturersById(lecturerId);
        if(data == null){
            Map<String, Object> errResp = new HashMap<String, Object>();
            errResp.put("error_message", "Lecturer data by id " + lecturerId + " not found.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(errResp);
        }

        lecturersRepository.delete(data);

        String respon = "Lecturer " + data.getName() + " has been deleted.";
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(respon);
    }
}
