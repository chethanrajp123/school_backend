package com.school_backend.Controller;

import com.school_backend.Entity.AdminSignUp;
import com.school_backend.Repository.AdminSignUpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@CrossOrigin("*")
public class AdminSignUpController
{
    @Autowired
    AdminSignUpRepository adminSignUpRepository;

    @PostMapping("/addData")
    public ResponseEntity<?> addData(@RequestBody AdminSignUp obj)
    {
        Optional<AdminSignUp> A=adminSignUpRepository.findByEmail(obj.getEmail());
        if(A.isPresent())
            return new ResponseEntity<>("Email Already Exists", HttpStatus.OK);
        else
        {
            adminSignUpRepository.save(obj);
            return new ResponseEntity<>("Successfully Created Account",HttpStatus.OK);
        }
    }

}
