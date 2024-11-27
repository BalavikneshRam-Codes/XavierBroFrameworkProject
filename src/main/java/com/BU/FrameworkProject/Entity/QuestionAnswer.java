package com.BU.FrameworkProject.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionAnswerId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "frameworkQuestionID")
    private FrameworkQuestion frameworkQuestionId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "frameworkRatingID")
    private FrameworkRating frameworkRatings;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "testId")
    private Test test;

}
