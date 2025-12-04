package com.capstone.sjseed.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
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
    @Setter
    private String phoneNumber;

    @Column(nullable = false)
    @ColumnDefault("0")
    @Setter
    private int coin;

    @Column(nullable = false)
    @ColumnDefault("'0000000'")
    @Setter
    private String attendedDays;

    @Column(nullable = false)
    @Setter
    private LocalDate lastAttendDate = LocalDate.now();

    @Column(nullable = false)
    @Setter
    private int consecutiveAttendDays = 1;

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
        this.coin = 10000;
        this.attendedDays = "0000000";
        this.lastAttendDate = LocalDate.now();
        this.premium = false;
        this.collection = collection;
    }

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Plant> plants = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Treatment> treatments = new ArrayList<>();

    public void initializeAttendedDays() {
        this.attendedDays = "0000000";
    }

    public void attendDay(int day) {
        StringBuilder sb = new StringBuilder(this.attendedDays);
        sb.setCharAt(day, '1');
        this.attendedDays = sb.toString();
    }
}
