package com.capstone.sjseed.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private Rarity rarity;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private String description;

    @OneToOne(fetch = FetchType.LAZY)
    private PlantSpecies species;

    @ManyToOne(fetch = FetchType.LAZY)
    private Collection collection;
}
