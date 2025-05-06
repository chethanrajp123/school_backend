package com.school_backend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Permission
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String acceptation;
//    private String eventdate;
//    private String lastdate;

//    @ManyToOne
//    @JoinColumn(name="standard")
//    private Grades gradesPermission;

    @ManyToOne
    @JoinColumn(name="parentid")
    private ParentSignUp parentSignUpPermission;

    @ManyToOne
    @JoinColumn(name="studentid")
    private Student studentPermission;

    @ManyToOne
    @JoinColumn(name="eventid")
    private Event eventPermission;

}
