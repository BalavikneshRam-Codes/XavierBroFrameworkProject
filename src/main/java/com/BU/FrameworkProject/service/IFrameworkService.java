package com.BU.FrameworkProject.service;

import com.BU.FrameworkProject.vo.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFrameworkService {

    FrameworkVO saveFramework(FrameworkVO frameworkVO) throws Exception;

    FrameworkVO findFramework(Long id) throws Exception;

    String  saveUser(UserVo userVo);

    TestVO takeTest(TestVO testVO) throws Exception;

    PercentageVO getScore(PercentageVO percentageVO) throws Exception;

    FileVO generateCSVExcelSheet() throws Exception;

    FileVO generateCSVSheet() throws Exception;

    String generateEmailForUser(PercentageVO percentageVO) throws Exception;
}
