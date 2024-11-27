package com.BU.FrameworkProject.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "frameworkQuestion")
public class  FrameworkQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "frameworkQuestion_Id")
    private Long frameworkQuestionId;

    @Column(name = "frameworkQuestion_Status")
    private String frameworkQuestionStatus;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "framework_ID")
    private Framework framework;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_ID")
    private Question question;
//
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "questionAnswerId")
//    private QuestionAnswer questionAnswer;

}
