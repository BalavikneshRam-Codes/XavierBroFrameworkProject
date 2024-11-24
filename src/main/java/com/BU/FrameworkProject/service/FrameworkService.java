package com.BU.FrameworkProject.service;

import com.BU.FrameworkProject.business.IFrameworkBusiness;
import com.BU.FrameworkProject.vo.FrameworkVO;
import com.BU.FrameworkProject.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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


}
