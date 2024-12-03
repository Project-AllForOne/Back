package com.banghyang.object.note.entity;

import com.banghyang.object.perfume.entity.Perfume;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MiddleNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String spices;

    @OneToOne
    @JoinColumn(name = "perfume_id", unique = true, nullable = false)
    @JsonIgnore
    private Perfume perfume;

    @Builder
    public MiddleNote(String spices, Perfume perfume) {
        this.spices = spices;
        this.perfume = perfume;
    }

    public void modify(MiddleNote modifyMiddleNoteEntity) {
        this.spices = modifyMiddleNoteEntity.getSpices();
        this.perfume = modifyMiddleNoteEntity.getPerfume();
    }
}
