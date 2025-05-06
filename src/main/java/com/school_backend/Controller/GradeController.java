package com.school_backend.Controller;

import com.school_backend.Entity.Grades;
import com.school_backend.Repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class GradeController
{
    @Autowired
    GradeRepository gradeRepository;

    @PostMapping("/addGrade/{grades}")
    public ResponseEntity<?> AddGrade(@PathVariable String grades)
    {
        Optional<Grades> G=gradeRepository.findByGradename(grades);
        if(G.isPresent())
            return new ResponseEntity<>("Grade Name Already exists", HttpStatus.OK);
        else
        {
            Grades grade = new Grades();
            grade.setGradename(grades);
            gradeRepository.save(grade);
            return new ResponseEntity<>("Grades Added Successfully",HttpStatus.OK);
        }
    }

    @GetMapping("/getGrades")
    public ResponseEntity<?> getGrade()
    {
        List<Grades> gradeList=gradeRepository.findAll();
        return new ResponseEntity<>(gradeList,HttpStatus.OK);
    }

}
