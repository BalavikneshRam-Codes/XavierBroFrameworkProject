package com.BU.FrameworkProject.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class NotesUserVO {
    private Long notes_id;
    private UserVo userVo;
    private String status;
}
