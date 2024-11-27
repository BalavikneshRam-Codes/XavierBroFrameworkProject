package com.BU.FrameworkProject.business;

import com.BU.FrameworkProject.Entity.Question;
import com.BU.FrameworkProject.repository.QuestionRepo;
import com.BU.FrameworkProject.vo.QuestionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class QuestionBusiness implements IQuestionBusiness{
    @Autowired
    private QuestionRepo questionRepo;

    @Override
    public QuestionVO saveQuestion(QuestionVO questionVO) throws Exception {

        if (validation(questionVO)) {
            try {
                Question question = convertVOtoEntity(questionVO);
                questionRepo.save(question);
                questionVO.setQuestionId(question.getQuestionId());
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
        return questionVO;
    }
    private Question convertVOtoEntity(QuestionVO questionVO){
        Question question = new Question();
        question.setQuestionName(StringUtils.trimAllWhitespace(questionVO.getQuestionName()));
        return question;
    }

    private boolean validation(QuestionVO questionVO){
        if(StringUtils.containsWhitespace(questionVO.getQuestionName())){
            return false;
        }
        return true;
    }












//    private QuestionVO convertEntityToVO(Question question){
//        QuestionVO questionVO = new QuestionVO();
//        questionVO.setQuestionId(question.getQuestionId());
//        questionVO.setQuestionName(question.getQuestionName());
//        return questionVO;
//    }
}
