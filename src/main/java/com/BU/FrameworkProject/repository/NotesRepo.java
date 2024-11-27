package com.BU.FrameworkProject.repository;

import com.BU.FrameworkProject.Entity.Notes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotesRepo extends JpaRepository<Notes,Long> {
//    boolean existsBynoteTitle(String title);
}
