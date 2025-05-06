package com.school_backend.Controller;

import com.school_backend.Repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
public class EventCountController
{
    @Autowired
    PermissionRepository permissionRepository;

    @GetMapping("/getAttendance/{eventid}")
    public ResponseEntity<?> GetAttendance(@PathVariable Integer eventid)
    {
        List<Integer> count=permissionRepository.findByCount(eventid,"Accepted");
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

}
