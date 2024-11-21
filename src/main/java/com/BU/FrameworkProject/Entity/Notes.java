package com.BU.FrameworkProject.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notes",uniqueConstraints = @UniqueConstraint(columnNames = {"title"}))
public class Notes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notes_id;

    @Column(name = "title")
    private String noteTitle;

    @Column(name = "description")
    private String noteDesc;

    @Column(name = "notes_status")
    private String noteStatus;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "notesId")
    private Set<NotesUser> notesUsers;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "notesId")
    private Set<ActionItem> actionItems;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "notesId")
    private Set<Attachment> attachments;
}
