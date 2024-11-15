package com.BU.FrameworkProject.vo;


import com.BU.FrameworkProject.util.ResponseStructure;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FrameworkVO extends ResponseStructure {
    private Long frameworkId;
    private String frameworkName;
    private String frameworkStatus;
    private Date frameworkDate;
    private Set<FrameworkQuestionVO> frameworkQuestions;
    private Set<FrameworkRatingVO> frameworkRatings;

}
