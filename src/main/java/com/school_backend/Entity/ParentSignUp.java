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
public class ParentSignUp
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String phonenumber;
    private String email;
    private String password;

    @OneToMany(mappedBy = "parentSignUp")
    @JsonIgnore
    private List<Student> studentList;

    @OneToMany(mappedBy = "parentSignUpPermission")
    @JsonIgnore
    private List<Permission> permissionList;

}
