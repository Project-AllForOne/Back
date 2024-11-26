package com.banghyang.object.perfume.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PerfumeImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @ManyToOne
    @JoinColumn(name = "perfume_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Perfume perfume;

    @Builder(toBuilder = true)
    public PerfumeImage(String url, Perfume perfume) {
        this.url = url;
        this.perfume = perfume;
    }
}
