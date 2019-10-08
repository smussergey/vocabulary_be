package com.le.app.repository;

import com.le.app.model.IrregularVerb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IrregularVerbRepository extends JpaRepository<IrregularVerb, Long> {
}