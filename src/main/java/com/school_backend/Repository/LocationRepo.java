package com.school_backend.Repository;

import com.school_backend.Entity.Location;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LocationRepo extends JpaRepository<Location,Long> {
    Location findByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE Location l SET l.distance = :distance WHERE l.id = :id")
    void updateDistanceById(@Param("id") Long id, @Param("distance") Double distance);

}
