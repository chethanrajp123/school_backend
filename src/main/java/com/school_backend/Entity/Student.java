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
public class Student
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne
    @JoinColumn(name="parentid")
    private ParentSignUp parentSignUp;

    @ManyToOne
    @JoinColumn(name="standard")
    private Grades gradesList;

    @OneToMany(mappedBy = "studentPermission")
    @JsonIgnore
    private List<Permission> permissionList;

}
