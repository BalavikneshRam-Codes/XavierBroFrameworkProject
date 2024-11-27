package com.BU.FrameworkProject.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.mapping.UniqueKey;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long TestId;

    @JoinColumn(name = "userId")
    @ManyToOne(cascade = CascadeType.ALL)
    private User userId;

    @JoinColumn(name = "frameworkId")
    @ManyToOne(cascade = CascadeType.ALL)
    private Framework frameworkId;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "test")
    private Set<QuestionAnswer> questionAnswers;
}
