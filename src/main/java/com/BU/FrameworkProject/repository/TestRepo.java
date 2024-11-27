package com.BU.FrameworkProject.repository;

import com.BU.FrameworkProject.Entity.Test;
import com.BU.FrameworkProject.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepo extends JpaRepository<Test, Long> {
    boolean existsByUserId(User user);
}
