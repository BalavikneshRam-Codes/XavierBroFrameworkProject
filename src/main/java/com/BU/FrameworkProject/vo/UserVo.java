package com.BU.FrameworkProject.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {
    private Long user_id;
    private String user_name;
    private String user_email;
}
