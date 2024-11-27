package com.BU.FrameworkProject.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentVo {
    private Long attachment_id;
    private Long order;
    private String fileName;
    private String FileUrl;
}
