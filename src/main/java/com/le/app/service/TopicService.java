package com.le.app.service;

import com.le.app.model.Topic;
import com.le.app.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TopicService {
    private final TopicRepository topicRepository;

    @Autowired
    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Transactional(readOnly = true)
    public Topic findByName(String name) {
        Topic result = topicRepository.findByName(name);
        return result;
    }
}