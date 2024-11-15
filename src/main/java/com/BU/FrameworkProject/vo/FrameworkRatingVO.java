package com.BU.FrameworkProject.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FrameworkRatingVO{
    private Long frameworkRatingId;
    private RatingVO ratingVO;
    private Long frameworkRating_Score;
}
