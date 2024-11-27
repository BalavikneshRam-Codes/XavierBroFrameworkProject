package com.BU.FrameworkProject.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "note_user",uniqueConstraints = @UniqueConstraint(columnNames = {"userId","notes_id"}))
public class NotesUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noteUserId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private User noteUser;

    @Column(name = "NotesStatus")
    private String noteStatus;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "notes_id")
    private Notes notesId;
}
