package com.BU.FrameworkProject.service;

import com.BU.FrameworkProject.vo.FrameworkVO;
import com.BU.FrameworkProject.vo.UserVo;
import org.springframework.web.multipart.MultipartFile;

public interface IFrameworkService {

    FrameworkVO saveFramework(FrameworkVO frameworkVO) throws Exception;

    FrameworkVO findFramework(Long id) throws Exception;

    String  saveUser(UserVo userVo);
}
