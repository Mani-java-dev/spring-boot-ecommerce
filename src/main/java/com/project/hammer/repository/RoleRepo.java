package com.project.hammer.repository;

import com.project.hammer.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepo extends JpaRepository<Role,Long> {

    @Query(value = "SELECT * FROM role",nativeQuery = true)
    List<Role> getAllRoles();

     Role findByRole(String role);
}
