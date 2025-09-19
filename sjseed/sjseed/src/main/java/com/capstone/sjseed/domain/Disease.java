package com.capstone.sjseed.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "disease")
public class Disease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String symptom;

    @Column(nullable = false)
    private String cause;

    @Column(nullable = false)
    private String prevent;

    @Column(nullable = false)
    private String cure;

    @ManyToOne(fetch = FetchType.LAZY)
    private PlantSpecies plantSpecies;
}
