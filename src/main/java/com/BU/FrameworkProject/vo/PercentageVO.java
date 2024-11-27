package com.BU.FrameworkProject.vo;

import com.BU.FrameworkProject.util.ResponseStructure;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PercentageVO extends ResponseStructure {
    Long testId;
    Long userId;
    String  userName;
    Long totalMarks;
    Double percentage;
    Long maxMarks;
}
