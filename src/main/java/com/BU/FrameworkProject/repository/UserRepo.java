package com.BU.FrameworkProject.repository;

import com.BU.FrameworkProject.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
}
