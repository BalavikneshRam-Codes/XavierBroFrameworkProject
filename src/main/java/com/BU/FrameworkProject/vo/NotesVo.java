package com.BU.FrameworkProject.vo;


import com.BU.FrameworkProject.util.ResponseStructure;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class NotesVo extends ResponseStructure {
    private Long notes_id;
    private String title;
    private String description;
    private String status;
    private List<NotesUserVO> notesUserVOS;
    private List<ActionItemVO> actionItemVOS;
    private List<AttachmentVo> attachmentVos;
}
