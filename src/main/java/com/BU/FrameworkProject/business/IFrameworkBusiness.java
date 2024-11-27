package com.BU.FrameworkProject.business;

import com.BU.FrameworkProject.Entity.User;
import com.BU.FrameworkProject.vo.*;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IFrameworkBusiness {
    FrameworkVO saveFrameworkBO(FrameworkVO frameworkVO) throws Exception;

    FrameworkVO findFrameworkBO(Long id) throws Exception;
    String saveUser(UserVo userVo);

    TestVO takeTest(TestVO testVO) throws Exception;

    PercentageVO getScoreByTestId(PercentageVO percentageVO) throws Exception;

    FileVO getFileStatus() throws Exception;

    FileVO getCSVForUser() throws Exception;

    String sendEmailUser(PercentageVO percentageVO) throws Exception;
}
