package com.school_backend.Repository;

import com.school_backend.Entity.Grades;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GradeRepository extends JpaRepository<Grades,Integer>
{
    Optional<Grades> findByGradename(String grade);
}
