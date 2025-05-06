package com.school_backend.Repository;

import com.school_backend.Entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission,Integer>
{
    Optional<Permission> findByParentSignUpPermissionEmail(String email);

    Optional<Permission> findByIdAndAcceptation(Integer id,String acceptation);

    @Query("select count(*) from Permission p where p.eventPermission.id=?1 and p.acceptation=?2")
    List<Integer> findByCount(Integer id,String acceptation);

}
