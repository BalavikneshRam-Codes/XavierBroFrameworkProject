package com.BU.FrameworkProject.controller;

import com.BU.FrameworkProject.service.IQuestionService;
import com.BU.FrameworkProject.vo.QuestionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class QuestionController {

    @Autowired
    private IQuestionService questionService;

    @PostMapping("/addQuestion")
    public QuestionVO saveQuestion(@RequestBody QuestionVO questionVO) throws Exception {
        try{
            questionVO =  questionService.saveQuestion(questionVO);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return questionVO;
    }
}
