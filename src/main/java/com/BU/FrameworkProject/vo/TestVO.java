package com.BU.FrameworkProject.vo;

import com.BU.FrameworkProject.Entity.Framework;
import com.BU.FrameworkProject.Entity.QuestionAnswer;
import com.BU.FrameworkProject.Entity.User;
import com.BU.FrameworkProject.util.ResponseStructure;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestVO extends ResponseStructure {
    private Long TestId;
    private UserVo userId;
    private FrameworkVO frameworkId;
    private Set<QuestionAnswerVO> questionAnswers;
}
