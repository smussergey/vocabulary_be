package com.le.app.repository;

import com.le.app.model.Article;
import com.le.app.model.IrregularVerb;
import com.le.app.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IrregularVerbRepository extends JpaRepository<IrregularVerb, Long> {
}