package com.school_backend.Repository;

import com.school_backend.Entity.AdminSignUp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminSignUpRepository extends JpaRepository<AdminSignUp,Integer>
{
    Optional<AdminSignUp> findByEmail(String email);
}
