package com.BU.FrameworkProject.business;

import com.BU.FrameworkProject.Entity.*;
import com.BU.FrameworkProject.repository.*;
import com.BU.FrameworkProject.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import io.micrometer.common.util.*;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Component
public class FrameworkBusiness implements IFrameworkBusiness {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private FrameworkRepo frameworkRepo;
    @Autowired
    private QuestionRepo questionRepo;

    @Autowired
    private FrameworkQuestionRepo frameworkQuestionRepo;
    @Autowired
    private RatingRepo ratingRepo;
    @Override
    public FrameworkVO saveFrameworkBO(FrameworkVO frameworkVO) throws Exception {
        if (validation(frameworkVO)) {
            try {
                Framework framework = convertVOtoEntityAndSave(frameworkVO);
//              FrameworkVO frameworkVO1 = convertEntityToVO(framework);
                frameworkRepo.save(framework);
//                frameworkVO.setFrameworkId(framework.getFrameworkId());
            } catch (Exception e) {
                frameworkVO.setMessage(e.getMessage());
                throw new Exception(e.getMessage());
            }
        }
        return frameworkVO;
    }

//    @Override
//    public FrameworkVO saveFrameworkBO(FrameworkVO frameworkVO) throws Exception {
//        return null;
//    }

    @Override
    public FrameworkVO findFrameworkBO(Long id) {
        Framework framework = findByIdFramework(id);
        return convertEntityToVO(framework);
    }

    @Override
    public String saveUser(UserVo userVo) {
        User user = new User();
        user.setUseEmail(userVo.getUser_email());
        user.setUserName(userVo.getUser_name());
        userRepo.save(user);
        return "User Saved successfully";
    }

    private Framework findByIdFramework(Long id){
        if(frameworkRepo.existsById(id)){
            return frameworkRepo.findById(id).orElseThrow();
        }
        return null;
    }
    private Framework convertVOtoEntityAndSave(FrameworkVO frameworkVO) {
        Framework framework = new Framework();
        framework.setFrameworkDate(new Date());
        framework.setFrameworkName(frameworkVO.getFrameworkName());
        framework.setFrameworkStatus(frameworkVO.getFrameworkStatus());
        Set<FrameworkQuestion> frameworkQuestions = frameworkQuestions(frameworkVO,framework);
        framework.setFrameworkQuestions(frameworkQuestions);
        Set<FrameworkRating> frameworkRatings = frameworkRatings(frameworkVO);
        framework.setFrameworkRatings(frameworkRatings);
        return framework;
    }

    private Set<FrameworkRating> frameworkRatings(FrameworkVO frameworkVO){
        Set<FrameworkRating> frameworkRatings = new LinkedHashSet<>();
        frameworkVO.getFrameworkRatings().forEach(frameworkRatingVO -> {

            FrameworkRating frameworkRating = new FrameworkRating();
            Rating rating = findByIdRating(frameworkRatingVO.getRatingVO().getRatingId());
            frameworkRating.setRating(rating);
            frameworkRatings.add(frameworkRating);
        });
        return frameworkRatings;
    }
    private Rating findByIdRating(Long id){
        if(ratingRepo.existsById(id)) {
            return ratingRepo.findById(id).orElseThrow();
        }return null;
    }

    private Set<FrameworkQuestion> frameworkQuestions(FrameworkVO frameworkVO,Framework framework){
        Set<FrameworkQuestion> frameworkQuestions = new LinkedHashSet<>();
        frameworkVO.getFrameworkQuestions()
                .forEach(frameworkQuestionVO -> {
                    FrameworkQuestion frameworkQuestion = new FrameworkQuestion();
                    frameworkQuestion.setFrameworkQuestionStatus("Active");

                    Question question = findByID(frameworkQuestionVO);
                    //question.setQuestionName(question.getQuestionName());
                    frameworkQuestion.setQuestion(question);
                    frameworkQuestion.setFramework(framework);
                    frameworkQuestions.add(frameworkQuestion);
                });
        return frameworkQuestions;
    }
    private Question findByID(FrameworkQuestionVO frameworkQuestionVO){
        if(frameworkQuestionRepo.existsById(frameworkQuestionVO.getQuestion().getQuestionId())) {
            return questionRepo.findById(frameworkQuestionVO.getQuestion().getQuestionId()).orElse(null);
        }
        return null;
    }

    private FrameworkVO convertEntityToVO(Framework framework) {
        FrameworkVO frameworkVO_ = new FrameworkVO();
        frameworkVO_.setFrameworkId(framework.getFrameworkId());
        frameworkVO_.setFrameworkName(framework.getFrameworkName());
        frameworkVO_.setFrameworkDate(framework.getFrameworkDate());
        frameworkVO_.setFrameworkStatus(framework.getFrameworkStatus());

        Set<FrameworkQuestionVO> frameworkQuestionVOS = frameworkQuestions(framework);
        frameworkVO_.setFrameworkQuestions(frameworkQuestionVOS);

//        Set<FrameworkRatingVO> frameworkRatingVOS = frameworkRating(framework);
//        frameworkVO_.setFrameworkRatings(frameworkRatingVOS);

        return frameworkVO_;
    }

    private Set<FrameworkRatingVO> frameworkRating(Framework framework){
        Set<FrameworkRatingVO> frameworkQuestionVOS = new LinkedHashSet<>();

        framework.getFrameworkRatings().forEach(rating -> {

            FrameworkRatingVO ratingVO = new FrameworkRatingVO();
            ratingVO.setFrameworkRatingId(rating.getFrameworkRatingId());

            RatingVO ratingDetailsVO = new RatingVO();
            ratingDetailsVO.setRatingId(rating.getRating().getRatingId());
            ratingDetailsVO.setRatingName(rating.getRating().getRatingName());

            ratingVO.setRatingVO(ratingDetailsVO);
            frameworkQuestionVOS.add(ratingVO);
        });
        return frameworkQuestionVOS;
    }

    private Set<FrameworkQuestionVO> frameworkQuestions(Framework framework) {
        Set<FrameworkQuestionVO> frameworkQuestionVOS = new LinkedHashSet<>();
        System.out.println(framework.getFrameworkQuestions());
        framework.getFrameworkQuestions()
                .forEach(question -> {
                    FrameworkQuestionVO questionVO = new FrameworkQuestionVO();
                    questionVO.setFrameworkQuestionId(question.getFrameworkQuestionId());
                    questionVO.setFrameworkQuestionStatus(question.getFrameworkQuestionStatus());

                    QuestionVO questionDetailsVO = new QuestionVO();
                    questionDetailsVO.setQuestionId(question.getQuestion().getQuestionId());
                    questionDetailsVO.setQuestionName(question.getQuestion().getQuestionName());

                    questionVO.setQuestion(questionDetailsVO);
                    frameworkQuestionVOS.add(questionVO);
                });
        return frameworkQuestionVOS;
    }
    private boolean validation(FrameworkVO frameworkVO) {
        if (StringUtils.isBlank(frameworkVO.getFrameworkName())) {
            frameworkVO.setMessage("framework contains whiteSpaces or Empty");
            return false;
        }
        return true;
    }
}
