package com.BU.FrameworkProject.business;

import com.BU.FrameworkProject.Entity.User;
import com.BU.FrameworkProject.vo.FrameworkVO;
import com.BU.FrameworkProject.vo.UserVo;

public interface IFrameworkBusiness {
    FrameworkVO saveFrameworkBO(FrameworkVO frameworkVO) throws Exception;

    FrameworkVO findFrameworkBO(Long id);
    String saveUser(UserVo userVo);
}
