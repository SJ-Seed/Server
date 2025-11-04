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
@Table(name = "collection")
public class Collection extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @ColumnDefault("0.0")
    private double complete;

    @Builder
    public Collection() {

    }

    @OneToMany(mappedBy = "collection", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Piece> pieces = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

    public void addPiece(Piece piece) {
        this.pieces.add(piece);
        piece.setCollection(this);
    }
}
