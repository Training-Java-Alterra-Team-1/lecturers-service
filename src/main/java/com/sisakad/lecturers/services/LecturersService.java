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

        Map<String, Object> response = new HashMap<String, Object>();
        if(request.getMajorId() == null || request.getMajorId() == 0){
            response.put("message", "failed");
            response.put("error", "Field majorId is mandatory and its value cannot be 0");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(response);
        }
        if(request.getLecturerName() == null || request.getLecturerName() == ""){
            response.put("message", "failed");
            response.put("error", "Field lecturerName is mandatory");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(response);
        }
        if(request.getDob() == null){
            response.put("message", "failed");
            response.put("error", "Field dob is mandatory");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(response);
        }
        if(request.getGender() == null || request.getGender() == ""){
            response.put("message", "failed");
            response.put("error", "Field gender is mandatory");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(response);
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
        response.put("message", "success");
        response.put("data", lecturer);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(response);
    }

    @SneakyThrows(Exception.class)
    @ApiOperation("Get all lecturers")
    public ResponseEntity<Object> getAllLecturers(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        List<Lecturers> lecturers = lecturersRepository.findAll();
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("message", "success");
        response.put("data", lecturers);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(response);
    }

    @SneakyThrows(Exception.class)
    @ApiOperation("Get lecturer by Id")
    public ResponseEntity<Object> getLecturerById(Integer lecturerId){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Lecturers lecturer = lecturersRepository.findLecturersById(lecturerId);
        Map<String, Object> response = new HashMap<String, Object>();
        if(!Optional.ofNullable(lecturer).isPresent()) {
            response.put("message", "failed");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body(response);
        }

        response.put("message", "success");
        response.put("data", lecturer);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(response);
    }

    @SneakyThrows(Exception.class)
    @ApiOperation("Get lecturer by Name, DoB, Address, Gender")
    public ResponseEntity<Object> getLecturerByData(String name, Date dob, String address, String gender){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        List<Lecturers> lecturers = new ArrayList<Lecturers>();

        if((name == null || name == "")
                && (dob == null)
                && (address == null || address == "")
                && (gender == null || gender == "")){
            lecturers = lecturersRepository.findAll();
        }else{
            lecturers = lecturersRepository.findLecturersByNameContainingIgnoreCaseOrDobOrGenderOrAddressContainingIgnoreCase(name, dob, gender, address);
        }

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("message", "success");
        response.put("data", lecturers);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(response);
    }

    @SneakyThrows(Exception.class)
    @ApiOperation("Update lecturer data by id")
    public ResponseEntity<Object> updateLecturerData(Integer lecturerId, LecturersDto request){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Lecturers lecturer = lecturersRepository.findLecturersById(lecturerId);
        Map<String, Object> response = new HashMap<String, Object>();
        if(!Optional.ofNullable(lecturer).isPresent()) {
            response.put("message", "failed");
            response.put("error", "Not found!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body(response);
        }

        if(request.getMajorId() == 0){
            response.put("message", "failed");
            response.put("error", "Field majorId value cannot be 0");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(response);
        }

        if(request.getLecturerName() != null && request.getLecturerName() != "") lecturer.setName(request.getLecturerName());
        if(request.getDob() != null) lecturer.setDob(request.getDob());
        if(request.getGender() != null || request.getGender() != "") lecturer.setGender(request.getGender());
        if(request.getAddress() != null || request.getAddress() != "") lecturer.setAddress(request.getAddress());

        LocalDateTime todayDateTime = LocalDateTime.now();
        lecturer.setUpdatedAt(todayDateTime);
        lecturersRepository.save(lecturer);

        response.put("message", "success");
        response.put("data", lecturer);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(response);
    }

    @SneakyThrows(Exception.class)
    @ApiOperation("Delete lecturer data by id")
    public ResponseEntity<Object> deleteLecturerData(Integer lecturerId){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Lecturers lecturer = lecturersRepository.findLecturersById(lecturerId);
        Map<String, Object> response = new HashMap<String, Object>();
        if(!Optional.ofNullable(lecturer).isPresent()) {
            response.put("message", "failed");
            response.put("error", "Not found!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body(response);
        }

        lecturersRepository.delete(lecturer);

        response.put("message", "success");
        response.put("data", "Selected lecturer has been deleted");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(response);
    }
}
