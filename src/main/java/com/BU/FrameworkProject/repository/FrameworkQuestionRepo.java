package com.BU.FrameworkProject.repository;

import com.BU.FrameworkProject.Entity.FrameworkQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface FrameworkQuestionRepo extends JpaRepository<FrameworkQuestion,Long> {
    boolean existsByFrameworkQuestionId(Long id);
}
