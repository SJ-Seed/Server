package com.capstone.sjseed.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "treatment")
public class Treatment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private String disease;

    private String symptoms;

    private String cause;

    private String cure;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    private Plant plant;

    @Builder
    public Treatment(Member member, String disease, String explain, String cause, String cure, String imageUrl, Plant plant) {
        this.member = member;
        this.date = LocalDate.now();
        this.disease = disease;
        this.symptoms = explain;
        this.cause = cause;
        this.cure = cure;
        this.imageUrl = imageUrl;
        this.plant = plant;
    }
}
