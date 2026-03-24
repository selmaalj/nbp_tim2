package com.jobfair.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "stat_boards",
        schema = "NBPT2",
        uniqueConstraints = @UniqueConstraint(name = "uk_stat_board_slug", columnNames = "slug"),
        indexes = @Index(name = "idx_stat_board_year", columnList = "year")
)
public class StatBoard extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36)
    private String id;

    @Column(nullable = false, length = 150)
    private String slug;

    @Column(nullable = false, length = 200)
    private String title;

    @Column
    private Integer year;

    @Column(length = 1000)
    private String description;

    @Builder.Default
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stat> stats = new ArrayList<>();
}
