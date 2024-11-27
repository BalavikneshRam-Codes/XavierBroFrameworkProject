package com.BU.FrameworkProject.service;

import com.BU.FrameworkProject.vo.QuestionVO;

public interface IQuestionService {
    QuestionVO saveQuestion(QuestionVO questionVO) throws Exception;
}
