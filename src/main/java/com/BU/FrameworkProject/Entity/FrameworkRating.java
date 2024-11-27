package com.BU.FrameworkProject.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "frameworkRating")
public class FrameworkRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "frameworkRating_Id")
    private Long frameworkRatingId;

    @JoinColumn(name = "rating_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private Rating rating;

    @JoinColumn(name = "framework_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private Framework framework;

    @Column(name = "frameworkRating_Score")
    private Long frameworkRatingScore;


}
