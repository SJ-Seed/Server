package com.capstone.sjseed.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "plant")
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 15, nullable = false)
    private String name;

    private double temperature;
    private double humidity;
    private double soilWater;

    private LocalDate wateredDate;

    private LocalDate broughtDate;

    private boolean diseased;

    private String plantId;

    @Builder
    public Plant(String name, PlantSpecies species, Member member) {
        this.name = name;
        this.member = member;
        this.broughtDate = LocalDate.now();
        this.species = species;
        this.diseased = false;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @Setter
    private PlantSpecies species;
}
