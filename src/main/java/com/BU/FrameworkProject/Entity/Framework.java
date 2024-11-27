package com.BU.FrameworkProject.Entity;


import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "framework")
public class Framework {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "framework_id")
    private Long frameworkId;

    @Column(name = "framework_Name")
    private String frameworkName;

    @Column(name = "framework_status")
    private String frameworkStatus;

    @Column(name = "framework_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date frameworkDate;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "framework")
    @Column(name = "frameworkQuestion_id")
    private Set<FrameworkQuestion> frameworkQuestions;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "framework")
    @Column(name = "frameworkRating_id")
    private Set<FrameworkRating> frameworkRatings;
}
