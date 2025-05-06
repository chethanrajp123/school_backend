package com.school_backend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Grades
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String gradename;

    @OneToMany(mappedBy = "gradesList")
    @JsonIgnore
    private List<Student> student;

    @OneToMany(mappedBy = "grades")
    @JsonIgnore
    private List<Event> events;

//    @OneToMany(mappedBy = "gradesPermission")
//    @JsonIgnore
//    private List<Permission> permissionList;

}
