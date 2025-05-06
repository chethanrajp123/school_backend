package com.school_backend.Controller;

import com.school_backend.Entity.ParentSignUp;
import com.school_backend.Repository.ParentSignUpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class ParentSignUpController
{

    @Autowired
    ParentSignUpRepository parentSignUpRepository;

    @PostMapping("/addParentData")
    public ResponseEntity<?> AddParentData(@RequestBody ParentSignUp obj)
    {
        Optional<ParentSignUp> P=parentSignUpRepository.findByEmail(obj.getEmail());
        if(P.isPresent())
            return new ResponseEntity<>("Account Already Exists", HttpStatus.OK);
        else
        {
            parentSignUpRepository.save(obj);
            return new ResponseEntity<>("Parent Account Created Successfully",HttpStatus.OK);
        }
    }

    @GetMapping("/getParents")
    public ResponseEntity<?> GetParents()
    {
        List<ParentSignUp> parentList=parentSignUpRepository.findAll();
        return new ResponseEntity<>(parentList,HttpStatus.OK);
    }

}
