package com.school_backend.Repository;

import com.school_backend.Entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event,Integer>
{
    Optional<Event> findByDateAndLastdate(String date,String lastdate);
}
