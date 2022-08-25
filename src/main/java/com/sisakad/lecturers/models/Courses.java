package com.sisakad.lecturers.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name="Courses")
@Table(name="courses")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Courses extends Audit<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = false, unique = true)
    private Integer id;

    @Column(name = "name", nullable = false, insertable = true, length = 50)
    private String name;

    @Column(name = "credits", nullable = false, insertable = true, length = 1)
    private Integer credits;
}
