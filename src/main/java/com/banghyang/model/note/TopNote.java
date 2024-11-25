package com.banghyang.model.note;

import com.banghyang.model.perfume.Perfume;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TopNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "perfume_id", unique = true, nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Perfume perfume;

    private String spices;
}
