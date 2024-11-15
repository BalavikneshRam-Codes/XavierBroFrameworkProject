package com.BU.FrameworkProject.repository;

import com.BU.FrameworkProject.Entity.Framework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface FrameworkRepo extends JpaRepository<Framework, Long> {
}
