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
public class Event
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String eventname;
    private String date;
    private String time;
    private String location;
    private String purpose;
    private int count;
    private String lastdate;

    @ManyToOne
    @JoinColumn(name="staffid")
    private Staff staff;

    @ManyToOne
    @JoinColumn(name="standard")
    private Grades grades;

    @OneToMany(mappedBy = "eventPermission")
    @JsonIgnore
    private List<Permission> permissionList;

}
