package com.school_backend.Repository;

import com.school_backend.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Integer>
{
    List<Student> findByParentSignUpId(Integer id);

    List<Student> findByGradesListId(Integer id);
}
