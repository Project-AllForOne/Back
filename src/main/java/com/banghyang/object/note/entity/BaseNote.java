package com.banghyang.object.note.entity;

import com.banghyang.object.perfume.entity.Perfume;
import com.banghyang.object.spice.entity.Spice;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "base_note", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Spice> spices;

    @OneToOne
    @JoinColumn(name = "perfume_id", unique = true, nullable = false)
    @JsonIgnore
    private Perfume perfume;

    @Builder
    public BaseNote(List<Spice> spices, Perfume perfume) {
        this.spices = spices;
        this.perfume = perfume;
    }

    public void modify(BaseNote modifyBaseNoteEntity) {
        this.spices = modifyBaseNoteEntity.getSpices();
        this.perfume = modifyBaseNoteEntity.getPerfume();
    }
}
