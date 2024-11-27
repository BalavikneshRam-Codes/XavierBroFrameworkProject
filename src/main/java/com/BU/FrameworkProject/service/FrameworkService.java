package com.BU.FrameworkProject.service;

import com.BU.FrameworkProject.business.IFrameworkBusiness;
import com.BU.FrameworkProject.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FrameworkService implements IFrameworkService{
    @Autowired
    private IFrameworkBusiness frameworkBusiness;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FrameworkVO saveFramework(FrameworkVO frameworkVO) throws Exception {
        return frameworkBusiness.saveFrameworkBO(frameworkVO);
    }

    @Override
    public FrameworkVO findFramework(Long id) throws Exception {
        return frameworkBusiness.findFrameworkBO(id);
    }


    @Override
    public String saveUser(UserVo userVo) {
        return frameworkBusiness.saveUser(userVo);
    }

    @Override
    public TestVO takeTest(TestVO testVO) throws Exception {
        return frameworkBusiness.takeTest(testVO);
    }

    @Override
    public PercentageVO getScore(PercentageVO percentageVO) throws Exception {
        return frameworkBusiness.getScoreByTestId(percentageVO);
    }

    @Override
    public FileVO generateCSVExcelSheet() throws Exception {
        return frameworkBusiness.getFileStatus();
    }

    @Override
    public FileVO generateCSVSheet() throws Exception {
        return frameworkBusiness.getCSVForUser();
    }

    @Override
    public String generateEmailForUser(PercentageVO percentageVO) throws Exception {
        return frameworkBusiness.sendEmailUser(percentageVO);
    }


}
