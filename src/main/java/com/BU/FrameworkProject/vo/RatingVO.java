package com.BU.FrameworkProject.vo;

import com.BU.FrameworkProject.util.ResponseStructure;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingVO{
    private Long ratingId;
    private String ratingName;
}