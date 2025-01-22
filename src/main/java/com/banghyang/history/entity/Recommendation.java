package com.banghyang.history.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table
@NoArgsConstructor
@Getter
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String perfumeName;
    private String perfumeBrand;
    private String perfumeGrade;
    private List<String> perfumeImageUrls;
    private String reason;
    private String situation;

    @ManyToOne
    @JoinColumn(name = "history_id")
    private History history;

    @Builder
    public Recommendation(
            String perfumeName,
            String perfumeBrand,
            String perfumeGrade,
            List<String> perfumeImageUrls,
            String reason,
            String situation,
            History history
    ) {
        this.perfumeName = perfumeName;
        this.perfumeBrand = perfumeBrand;
        this.perfumeGrade = perfumeGrade;
        this.perfumeImageUrls = perfumeImageUrls;
        this.reason = reason;
        this.situation = situation;
        this.history = history;
    }
}
