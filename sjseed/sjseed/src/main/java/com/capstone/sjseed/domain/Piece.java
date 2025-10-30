package com.capstone.sjseed.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "piece")
public class Piece {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @OneToOne(fetch = FetchType.LAZY)
    private PlantSpecies species;

    @ManyToOne(fetch = FetchType.LAZY)
    private Collection collection;

    @Builder
    public Piece(PlantSpecies species, Collection collection) {
        this.date = LocalDate.now();
        this.species = species;
        this.collection = collection;
    }
}
