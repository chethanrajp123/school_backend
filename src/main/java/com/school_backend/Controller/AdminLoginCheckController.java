package com.school_backend.Controller;

import com.school_backend.Entity.AdminSignUp;
import com.school_backend.Repository.AdminSignUpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@CrossOrigin("*")
public class AdminLoginCheckController

{
    @Autowired
    AdminSignUpRepository adminSignUpRepository;

    @PostMapping("/loginCheck/{email}/{password}")
    public ResponseEntity<?> LoginCheck(@PathVariable String email,@PathVariable String password)
    {
        Optional<AdminSignUp> A=adminSignUpRepository.findByEmail(email);
        if(A.isPresent())
        {
            AdminSignUp adminSignUp=A.get();
            if(adminSignUp.getPassword().equals(password))
                return new ResponseEntity<>("Correct Password", HttpStatus.OK);
            else
                return new ResponseEntity<>("Incorrect Password",HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Account not Exists",HttpStatus.OK);
    }

}
