package com.le.app.repository;

import com.le.app.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    Topic findByName(String name);
}