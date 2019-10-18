package com.le.app.repository;

import com.le.app.model.User;
import com.le.app.model.dto.UserProfileDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

//    UserProfileDto findByUsernameFetchProfile(String username);

    @Query("select u from User u inner join fetch u.roles where u.username = :username")
    User findByUsernameWithRoles(@Param("username") String username);

}
