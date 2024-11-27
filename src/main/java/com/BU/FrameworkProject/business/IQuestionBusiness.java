package com.BU.FrameworkProject.business;

import com.BU.FrameworkProject.vo.QuestionVO;

public interface IQuestionBusiness {
    QuestionVO saveQuestion(QuestionVO questionVO) throws Exception;
}
