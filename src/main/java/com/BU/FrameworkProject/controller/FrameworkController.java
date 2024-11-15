package com.BU.FrameworkProject.controller;

import com.BU.FrameworkProject.service.IFrameworkService;
import com.BU.FrameworkProject.vo.FrameworkVO;
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

    @PostMapping("findFramework")
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


}
