package com.BU.FrameworkProject.controller;

import com.BU.FrameworkProject.service.FrameworkService;
import com.BU.FrameworkProject.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private FrameworkService frameworkService;

    @PutMapping("/addUser")
    public String saveUser(@RequestBody UserVo userVo){
        try {
            return frameworkService.saveUser(userVo);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
