package com.school_backend.Controller;

import com.school_backend.Entity.ParentSignUp;
import com.school_backend.Entity.Student;
import com.school_backend.Repository.ParentSignUpRepository;
import com.school_backend.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class ParentLoginController
{
    @Autowired
    ParentSignUpRepository parentSignUpRepository;

    @Autowired
    StudentRepository studentRepository;

    @PostMapping("/parentLoginChk/{email}/{password}")
    public ResponseEntity<?> ParentLoginCheck(@PathVariable String email,@PathVariable String password)
    {
        Optional<ParentSignUp> P=parentSignUpRepository.findByEmail(email);
        if(P.isPresent())
        {
            ParentSignUp parentSignUp = P.get();
            if (parentSignUp.getPassword().equals(password))
                return new ResponseEntity<>("Correct Password", HttpStatus.OK);
            else
                return new ResponseEntity<>("Wrong Password", HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Account Doesn't Exists",HttpStatus.OK);
    }

    @PostMapping("/GetStudentByEmail")
    public ResponseEntity<?> getStudentByEmail(@RequestParam String email) {
        var parent = parentSignUpRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Parent not found"));

        // Fetching students associated with the parent ID
        List<Student> students = studentRepository.findByParentSignUpId(parent.getId());

        // Returning parent and students information in the response
        return ResponseEntity.ok(Map.of(
                "parentName", parent.getName(),
                "parentEmail", parent.getEmail(),
                "parentPhone", parent.getPhonenumber(),
                "students", students
        ));
    }

}
