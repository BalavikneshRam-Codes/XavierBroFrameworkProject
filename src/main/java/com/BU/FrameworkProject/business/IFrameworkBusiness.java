package com.BU.FrameworkProject.business;

import com.BU.FrameworkProject.vo.FrameworkVO;

public interface IFrameworkBusiness {
    FrameworkVO saveFrameworkBO(FrameworkVO frameworkVO) throws Exception;

    FrameworkVO findFrameworkBO(Long id);
}
