package com.BU.FrameworkProject.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FrameworkQuestionVO{
    private Long frameworkQuestionId;
    private String frameworkQuestionStatus;
    private QuestionVO question;
    private List<FrameworkRatingVO> frameworkRatingVOS;
}
