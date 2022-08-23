package com.sisakad.lecturers.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name="LecturerCourses")
@Table(name="lecturercourses")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LecturerCourses extends Audit<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = false, unique = true)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="lecturerId", nullable = false, insertable = true, referencedColumnName = "id")
    private Lecturers lecturers;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="courseId", nullable = false, insertable = true, referencedColumnName = "id")
    private Courses courses;

    @Column(name="startedAt", nullable = false, insertable = true)
    private Date startedAt;

    @Column(name="endedAt", nullable = false, insertable = true)
    private Date endedAt;
}
