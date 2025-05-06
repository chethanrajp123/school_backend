package com.school_backend.Repository;

import com.school_backend.Entity.ParentSignUp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParentSignUpRepository extends JpaRepository<ParentSignUp,Integer>
{
    Optional<ParentSignUp> findByEmail(String email);
}
