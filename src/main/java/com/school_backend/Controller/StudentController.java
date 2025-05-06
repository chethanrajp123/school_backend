package com.school_backend.Controller;

import com.school_backend.DTO.EmailData;
import com.school_backend.Entity.*;
import com.school_backend.Repository.*;
import com.school_backend.Smtpservice.SmtpServiceClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class StudentController
{
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    GradeRepository gradeRepository;

    @Autowired
    ParentSignUpRepository parentSignUpRepository;

    @Autowired
    SmtpServiceClass smtpServiceClass;

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    EventRepository eventRepository;

    @PostMapping("/addStudent/{std}/{parentid}")
    public ResponseEntity<?> AddStudent(@RequestBody Student obj, @PathVariable Integer std,@PathVariable Integer parentid)
    {
        Optional<Grades> G=gradeRepository.findById(std);
        Optional<ParentSignUp> P=parentSignUpRepository.findById(parentid);
        if(G.isPresent() && P.isPresent())
        {
            ParentSignUp parentSignUp=P.get();
            Grades grades=G.get();
            obj.setGradesList(grades);
            obj.setParentSignUp(parentSignUp);
            studentRepository.save(obj);
            return new ResponseEntity<>("Student Name added Sucessfully", HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Grade not found",HttpStatus.OK);
    }

    @GetMapping("/parentList/{std}/{lastdate}/{date}")
    public ResponseEntity<?> ParentList(@PathVariable Integer std,@PathVariable String lastdate,@PathVariable String date)
    {
        List<Student> studentList=studentRepository.findByGradesListId(std);
        //System.out.println(studentList.size());
        for(int i=0;i<studentList.size();i++)
        {
            Student student=studentList.get(i);
            ParentSignUp parentSignUp=student.getParentSignUp();

            Permission permission=new Permission();
            permission.setAcceptation("Pending");
            //permission.setGradesPermission(student.getGradesList());
            permission.setParentSignUpPermission(parentSignUp);
            permission.setStudentPermission(student);
//            permission.setLastdate(lastdate);
//            permission.setEventdate(date);
            Optional<Event> E=eventRepository.findByDateAndLastdate(date,lastdate);
            if(E.isPresent())
            {
                Event event=E.get();
                permission.setEventPermission(event);
            }
            else
            {
                return new ResponseEntity<>("Event Id can't assign",HttpStatus.OK);
            }

            permissionRepository.save(permission);

            EmailData emailData=new EmailData();
            emailData.setRecepient(parentSignUp.getEmail());
            emailData.setSubject("Acceptation of Tour For Your Student");
            emailData.setMessage("Dear Student from our School we are organising a Tour for "+student.getGradesList().getGradename()+" for your child "+student.getName()+" Please accept the permission before "+lastdate);

            String msg=smtpServiceClass.SendEmail(emailData);

            if(msg.equals("Mail can't send some error!!"))
                return new ResponseEntity<>(parentSignUp.getEmail()+" for this particular Email can't send a message",HttpStatus.OK);
            else
                continue;
        }
        return new ResponseEntity<>("Mail sent to all Parents Successfully",HttpStatus.OK);
    }

    @GetMapping("/getStudents/{std1}")
    public ResponseEntity<?> GetStudents(@PathVariable Integer std1)
    {
        //System.out.println(std1);
        List<Student> studentList=studentRepository.findByGradesListId(std1);
        return new ResponseEntity<>(studentList,HttpStatus.OK);
    }

}
