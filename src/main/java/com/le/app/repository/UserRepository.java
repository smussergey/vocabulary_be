package com.le.app.repository;

import com.le.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query("select u from User u inner join fetch u.roles where u.username = :username")
    User findByUsernameWithRoles(@Param("username") String username);

}
