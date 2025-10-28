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

    @ManyToOne(fetch = FetchType.LAZY)
    private Plant plant;

    @Builder
    public Treatment(Member member, String disease, Plant plant) {
        this.member = member;
        this.date = LocalDate.now();
        this.disease = disease;
        this.plant = plant;
    }
}
