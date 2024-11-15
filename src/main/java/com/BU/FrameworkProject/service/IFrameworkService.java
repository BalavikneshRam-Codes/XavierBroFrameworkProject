package com.BU.FrameworkProject.service;

import com.BU.FrameworkProject.vo.FrameworkVO;

public interface IFrameworkService {

    FrameworkVO saveFramework(FrameworkVO frameworkVO) throws Exception;

    FrameworkVO findFramework(Long id) throws Exception;
}
