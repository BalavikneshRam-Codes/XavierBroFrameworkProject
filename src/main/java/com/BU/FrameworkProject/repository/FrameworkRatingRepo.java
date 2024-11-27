package com.BU.FrameworkProject.repository;

import com.BU.FrameworkProject.Entity.FrameworkRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface FrameworkRatingRepo extends JpaRepository<FrameworkRating,Long> {
}
