package com.le.app.repository;

import  com.le.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String name);
}
