package com.project.hammer.repository;

import com.project.hammer.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {

    List<Users> findByName(String name);

    @Query(value = "SELECT * FROM users WHERE gmail = :gmail AND active_status='ACTIVE'",nativeQuery = true)
    Users getGmailForUser(@Param("gmail") String gmail);

    @Query(value = "SELECT * FROM users WHERE gmail = :gmail",nativeQuery = true)
    Users getGmailForUserForAct(@Param("gmail") String gmail);

    @Query(value = "select * from users",nativeQuery = true)
    List<Users> getAllUsers();

    @Query(value = "select * from users where gmail != :gmail",nativeQuery = true)
    List<Users> getAllUsersExcludeLoginUser(@Param("gmail")String gmail);

    @Query(value = "SELECT * FROM users WHERE name = :userName",nativeQuery = true)
    Users getUserByUserName(@Param("userName")String userName);


}
