package com.BU.FrameworkProject.repository;

import com.BU.FrameworkProject.Entity.QuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionAnswerRepo extends JpaRepository<QuestionAnswer,Long> {
}
