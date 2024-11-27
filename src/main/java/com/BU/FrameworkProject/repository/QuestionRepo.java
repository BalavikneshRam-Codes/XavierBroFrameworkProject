package com.BU.FrameworkProject.repository;

import com.BU.FrameworkProject.Entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface QuestionRepo extends JpaRepository<Question,Long> {
}
