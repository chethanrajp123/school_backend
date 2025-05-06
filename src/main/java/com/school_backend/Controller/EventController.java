package com.school_backend.Controller;

import com.school_backend.Entity.Event;
import com.school_backend.Entity.Grades;
import com.school_backend.Entity.Staff;
import com.school_backend.Repository.EventRepository;
import com.school_backend.Repository.GradeRepository;
import com.school_backend.Repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class EventController
{
    @Autowired
    EventRepository eventRepository;

    @Autowired
    GradeRepository gradeRepository;

    @Autowired
    StaffRepository staffRepository;

    @PostMapping("/addEvent/{std}/{staffid}")
    public ResponseEntity<?> addEvents(@RequestBody Event obj, @PathVariable Integer std,@PathVariable Integer staffid)
    {
        Optional<Grades> G=gradeRepository.findById(std);
        Optional<Staff> S=staffRepository.findById(staffid);
        if(G.isPresent() && S.isPresent())
        {
            Grades grades=G.get();
            Staff staff=S.get();
            obj.setGrades(grades);
            obj.setStaff(staff);
            eventRepository.save(obj);
            return new ResponseEntity<>("Event added Successfully", HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Grade not found",HttpStatus.OK);
    }

    @GetMapping("/getEvents")
    public ResponseEntity<?> GetEvents()
    {
        List<Event> EventList=eventRepository.findAll();
        return new ResponseEntity<>(EventList,HttpStatus.OK);
    }

}
