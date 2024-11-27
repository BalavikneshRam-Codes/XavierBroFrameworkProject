package com.BU.FrameworkProject.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActionItemVO {
    private Long action_id;
    private Long action_order;
    private String free_Text;
}
