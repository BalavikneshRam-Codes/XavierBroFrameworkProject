package com.BU.FrameworkProject.vo;

import com.BU.FrameworkProject.Entity.FrameworkQuestion;
import com.BU.FrameworkProject.Entity.FrameworkRating;
import com.BU.FrameworkProject.Entity.Test;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionAnswerVO {

    private Long questionAnswerId;
    private FrameworkQuestionVO frameworkQuestionVO;
    private FrameworkRatingVO frameworkRatingVO;
}
