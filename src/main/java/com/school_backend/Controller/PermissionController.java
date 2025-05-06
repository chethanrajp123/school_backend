package com.school_backend.Controller;

import com.school_backend.Entity.ParentSignUp;
import com.school_backend.Entity.Permission;
import com.school_backend.Repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class PermissionController
{
    @Autowired
    PermissionRepository permissionRepository;

    @GetMapping("/getParentIdFromPermission/{parentemail}")
    public ResponseEntity<?> GetParentIdFromPermission(@PathVariable String parentemail)
    {
        Optional<Permission> P=permissionRepository.findByParentSignUpPermissionEmail(parentemail);
        if(P.isPresent())
        {
            Permission permission=P.get();
            if(permission.getAcceptation().equals("Pending"))
                return new ResponseEntity<>(permission, HttpStatus.OK);
            else
                return new ResponseEntity<>("Accepted",HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Parent Id Not found in Permission table",HttpStatus.OK);
    }

    @PutMapping("/acceptation/{id}")
    public ResponseEntity<?> Acceptation(@PathVariable Integer id) throws ParseException {
        Optional<Permission> P=permissionRepository.findByIdAndAcceptation(id,"Pending");
        if(P.isPresent())
        {
            Permission permission=P.get();

            Date d=new Date();
            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
            String pdate=dateFormat.format(d);
            String ldate=permission.getEventPermission().getLastdate();

            Date presentDate=dateFormat.parse(pdate);
            Date lastDate=dateFormat.parse(ldate);

            if(presentDate.before(lastDate)||presentDate.equals(lastDate))
            {
                permission.setAcceptation("Accepted");
                permissionRepository.save(permission);
                return new ResponseEntity<>("You Accepted for Tour",HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity<>("Date has Expired",HttpStatus.OK);
            }

        }
        else
            return new ResponseEntity<>("You are already Accepted",HttpStatus.OK);
    }

}
