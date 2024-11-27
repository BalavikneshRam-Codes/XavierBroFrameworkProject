package com.BU.FrameworkProject.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "action_item")
public class ActionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "action_Id")
    private Long actionItemId;

    @Column(name = "action_order")
    private Long actionOrder;

    @Column(name = "free_text",length = 50000)
    private String freeText;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "notes_id")
    private Notes notesId;
}
