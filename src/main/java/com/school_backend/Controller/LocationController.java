package com.school_backend.Controller;


import com.school_backend.Entity.Event;
import com.school_backend.Entity.Location;
import com.school_backend.Entity.ParentSignUp;
import com.school_backend.Entity.Student;
import com.school_backend.Repository.LocationRepo;
import com.school_backend.Repository.ParentSignUpRepository;
import com.school_backend.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")

public class LocationController {

    @Autowired
    private LocationRepo locationRepo;

    @Autowired
    private ParentSignUpRepository parentSignUpRepository;

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping("/UpdateLocation")
    public ResponseEntity<?> updateUserLocation(
            @RequestParam String email,
            @RequestParam double latitude,
            @RequestParam double longitude) {

        ParentSignUp parentSignUp = parentSignUpRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch the first student associated with the parent
        List<Student> students = studentRepository.findByParentSignUpId(parentSignUp.getId());
        Student student = students.isEmpty() ?
                null : students.get(0);  // Use the first student in the list, or handle accordingly

        if (student == null) {
            throw new RuntimeException("Student not found");
        }

        Location location = locationRepo.findByEmail(email);

        if (location != null) {
            // Update the existing location
            location.setLatitude(latitude);
            location.setLongitude(longitude);
            location.setStudent(student.getName());
            location.setMobile(parentSignUp.getPhonenumber());
            locationRepo.save(location);
            return ResponseEntity.ok("Location updated successfully.");
        } else {
            // Create a new location if none exists
            Location newLocation = new Location();
            newLocation.setEmail(email);
            newLocation.setLatitude(latitude);
            newLocation.setLongitude(longitude);
            newLocation.setStudent(student.getName());
            newLocation.setMobile(parentSignUp.getPhonenumber());
            locationRepo.save(newLocation);
            return ResponseEntity.ok("Location created successfully.");
        }
    }


    @PostMapping("/UpdateThreshold")
    public ResponseEntity<?> updateThreshold(@RequestParam String threshold) {
        // Retrieve all locations
        List<Location> locations = locationRepo.findAll();

        // Update the threshold for each location
        for (Location location : locations) {
            location.setThreshold(threshold);
        }
        // Save all updated locations
        locationRepo.saveAll(locations);
        return ResponseEntity.ok("Threshold updated for all locations successfully.");
    }

    @PostMapping("/GetLocationByEmail")
    public ResponseEntity<?> getStudentByEmail(@RequestParam String email) {
        var location = locationRepo.findByEmail(email);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    @GetMapping("/GetStudents/{targetLatitude}/{targetLongitude}")
    public ResponseEntity<List<Location>> getNearestStudents(
            @PathVariable double targetLatitude,
            @PathVariable double targetLongitude
    ) {
        List<Location> allStudents = locationRepo.findAll();

        // Calculate distances in meters and set them for each student
        for (Location location : allStudents) {
            double distanceInMeters = calculateDistance(targetLatitude, targetLongitude, location.getLatitude(), location.getLongitude());

            // Round the distance to the nearest 50 meters
            distanceInMeters = roundToNearest(distanceInMeters, 50); // Change 50 to 100 if needed
            location.setDistance(distanceInMeters); // Ensure Location has a setDistance method

            // Update distance in database
            locationRepo.updateDistanceById(location.getId(), distanceInMeters);

            // Set the updated distance in the object for response
            location.setDistance(distanceInMeters);

        }


        return ResponseEntity.ok(allStudents);
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371000; // Radius of the Earth in meters

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c; // Distance in meters
    }

    private double roundToNearest(double value, double roundTo) {
        return Math.round(value / roundTo) * roundTo;
    }

}


