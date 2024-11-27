package com.BU.FrameworkProject.controller;

import com.BU.FrameworkProject.service.IFrameworkService;
import com.BU.FrameworkProject.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class FrameworkController {
    @Autowired
    private IFrameworkService frameworkService;

    @PostMapping("addFramework")
    public FrameworkVO saveFramework(@RequestBody FrameworkVO frameworkVO) throws Exception {
        try {
            frameworkVO = frameworkService.saveFramework(frameworkVO);
            frameworkVO.setStatusCode(HttpStatus.OK.value());
            frameworkVO.setMessage("Success");
        } catch (Exception e) {
            frameworkVO.setMessage(e.getMessage());
            throw new Exception(e.getMessage());
        }
        return frameworkVO;
    }

    @GetMapping("findFramework")
    public FrameworkVO findFramework(@RequestBody FrameworkVO frameworkVO){
        if (frameworkVO.getFrameworkId() != null) {
        try {
             frameworkVO = frameworkService.findFramework(frameworkVO.getFrameworkId());
             frameworkVO.setStatusCode(HttpStatus.OK.value());
             frameworkVO.setMessage("Success");
            }catch (Exception e){
                    frameworkVO.setMessage(e.getMessage());
                    frameworkVO.setStatusCode(HttpStatus.NO_CONTENT.value());
             }
            return frameworkVO;
        }
        return null;
    }

    @PostMapping("takeTest")
    public TestVO takeTest(@RequestBody TestVO testVO){
        try {
            frameworkService.takeTest(testVO);
            testVO.setStatusCode(HttpStatus.OK.value());
            testVO.setMessage(HttpStatus.ACCEPTED.getReasonPhrase());
        }catch (Exception e){
            testVO.setStatusCode(HttpStatus.NOT_EXTENDED.value());
            testVO.setMessage(HttpStatus.NOT_ACCEPTABLE.getReasonPhrase());
        }
        return testVO;
    }

    @GetMapping("viewScore")
    public PercentageVO score(@RequestBody PercentageVO percentageVO){
        try {
             frameworkService.getScore(percentageVO);
             percentageVO.setStatusCode(HttpStatus.OK.value());
             percentageVO.setMessage(HttpStatus.OK.getReasonPhrase());
        }catch (Exception e){
            percentageVO.setPercentage((double) HttpStatus.NO_CONTENT.value());
            percentageVO.setStatusCode(HttpStatus.NOT_FOUND.value());
            percentageVO.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
        }
        return percentageVO;
    }

    @GetMapping("getExcelSheetForUser")
    public FileVO generateCSVandExcelSheet(){
        try {
           FileVO fileVo = frameworkService.generateCSVExcelSheet();
           fileVo.setStatusCode(HttpStatus.OK.value());
           fileVo.setMessage(HttpStatus.OK.name());
           return fileVo;
        }catch (Exception e){
            FileVO fileVO = new FileVO();
            fileVO.setFileStatus("File not Uploaded");
            fileVO.setMessage(HttpStatus.NOT_ACCEPTABLE.getReasonPhrase());
            fileVO.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
            return fileVO;
        }
    }

    @GetMapping("getCSVSheetForUser")
    public FileVO generateCSVForUser(){
        try {
            FileVO fileVO = frameworkService.generateCSVSheet();
            fileVO.setStatusCode(HttpStatus.OK.value());
            fileVO.setMessage(HttpStatus.OK.name());
            return fileVO;
        }catch (Exception e){
            FileVO fileVO = new FileVO();
            fileVO.setFileStatus("File not Uploaded");
            fileVO.setMessage(HttpStatus.NOT_ACCEPTABLE.getReasonPhrase());
            fileVO.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
            return fileVO;
        }
    }

    @GetMapping("sendEmail")
    public String sendEmailForUser(@RequestBody PercentageVO percentageVO){
        try {
            return frameworkService.generateEmailForUser(percentageVO);
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
    }


}
