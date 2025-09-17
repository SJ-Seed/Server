package com.capstone.sjseed.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false, unique = true)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int coin;

    @Column(nullable = false)
    @ColumnDefault("'0000000'")
    private String attendedDays;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean premium;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "collection_id", unique = true,  nullable = false)
    private Collection collection;

    @Builder
    public Member(String name, int year, String loginId, String password, String phoneNumber, Collection collection) {
        this.name = name;
        this.year = year;
        this.loginId = loginId;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.coin = 0;
        this.attendedDays = "0000000";
        this.premium = false;
        this.collection = collection;
    }

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Plant> plants = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Treatment> treatments = new ArrayList<>();
}
