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
public class SingleNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String spices;

    @OneToOne
    @JoinColumn(name = "perfume_id", unique = true, nullable = false)
    @JsonIgnore
    private Perfume perfume;

    @Builder(toBuilder = true)
    public SingleNote(String spices, Perfume perfume) {
        this.spices = spices;
        this.perfume = perfume;
    }

    public SingleNote modify(SingleNote modifySingleNoteEntity) {
        this.spices = modifySingleNoteEntity.getSpices();
        this.perfume = modifySingleNoteEntity.getPerfume();
        return this;
    }
}
