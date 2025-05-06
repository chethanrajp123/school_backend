package com.school_backend.Controller;

import com.school_backend.DTO.UserDto;
import com.school_backend.Entity.ParentSignUp;
import com.school_backend.Entity.Staff;
import com.school_backend.Repository.ParentSignUpRepository;
import com.school_backend.Repository.StaffRepository;
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
public class StaffController
{
    @Autowired
    StaffRepository staffRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    ParentSignUpRepository parentSignUpRepository;

    @PostMapping("/addStaff")
    public ResponseEntity<?> AddStaff(@RequestBody Staff obj)
    {
        Optional<Staff> S=staffRepository.findByEmail(obj.getEmail());
        if(S.isPresent())
            return new ResponseEntity<>("Staff Already Exists", HttpStatus.OK);
        else
        {
            staffRepository.save(obj);
            return new ResponseEntity<>("Staff added Successfully",HttpStatus.OK);
        }
    }

    @GetMapping("/getStaff")
    public ResponseEntity<?> GetStaff()
    {
        List<Staff> staffList=staffRepository.findAll();
        return new ResponseEntity<>(staffList,HttpStatus.OK);
    }

    @PostMapping("/GetStaffByEmail")
    public ResponseEntity<?> getStaffByEmail(@RequestParam String email) {
        var staff = staffRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Assuming `staff` has a `username` field or modify according to your model
        return ResponseEntity.ok(Map.of(
                "username", staff.getStaffname(),
                "email", staff.getEmail(),
                "phone", staff.getPhonenumber() // include any other necessary fields
        ));
    }

    @PostMapping("/UserLogin")
    public ResponseEntity<?> userLogin(
            @RequestParam String role,
            @RequestParam String email,
            @RequestParam String password
    ) {
        if ("Incharge".equals(role)) {
            var incharge = staffRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            if (incharge.getPassword().equals(password)) {
                return new ResponseEntity<>(Map.of("role", "Incharge", "data", incharge), HttpStatus.OK);
            } else {
                throw new RuntimeException("Invalid Password");
            }
        } else if ("Student".equals(role)) {
            var student = parentSignUpRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            if (student.getPassword().equals(password)) {
                return new ResponseEntity<>(Map.of("role", "Student", "data", student), HttpStatus.OK);
            } else {
                throw new RuntimeException("Invalid Password");
            }
        } else {
            throw new RuntimeException("Select User Type");
        }
    }

}
