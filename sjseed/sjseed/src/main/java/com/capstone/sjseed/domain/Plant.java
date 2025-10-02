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

    @Setter
    private double temperature;

    @Setter
    private double humidity;

    @Setter
    private double soilWater;

    @Setter
    private LocalDate wateredDate;

    private LocalDate broughtDate;

    private boolean diseased;

    private String plantId;

    @Builder
    public Plant(String name, String plantId, Member member) {
        this.name = name;
        this.member = member;
        this.broughtDate = LocalDate.now();
        this.plantId = plantId;
        this.diseased = false;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @Setter
    private PlantSpecies species;
}
