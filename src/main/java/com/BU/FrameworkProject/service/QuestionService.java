package com.BU.FrameworkProject.service;

import com.BU.FrameworkProject.business.IQuestionBusiness;
import com.BU.FrameworkProject.vo.QuestionVO;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService implements IQuestionService{
    @Autowired
    private IQuestionBusiness questionBusiness;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QuestionVO saveQuestion(QuestionVO questionVO) throws Exception {
        return questionBusiness.saveQuestion(questionVO);
    }
}
