package com.BU.FrameworkProject.repository;

import com.BU.FrameworkProject.Entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface RatingRepo extends JpaRepository<Rating,Long> {
}
