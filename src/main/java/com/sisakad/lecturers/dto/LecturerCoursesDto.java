package com.sisakad.lecturers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LecturerCoursesDto {
    private Integer lecturerId;
    private Integer courseId;
    private Date startedAt;
    private Date endedAt;
}
