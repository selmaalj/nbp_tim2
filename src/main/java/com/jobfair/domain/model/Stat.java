package com.jobfair.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
        name = "stats",
        schema = "NBPT2",
        uniqueConstraints = @UniqueConstraint(name = "uk_stat_board_sort", columnNames = {"board_id", "sort"}),
        indexes = @Index(name = "idx_stat_board", columnList = "board_id")
)
public class Stat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false, foreignKey = @ForeignKey(name = "fk_stat_board"))
    private StatBoard board;

    @Column(nullable = false, length = 200)
    private String label;

    @Column(name = "value_int")
    private Integer value;

    @Column(name = "value_text", length = 200)
    private String valueText;

    @Column(nullable = false)
    private boolean plus;

    @Column(nullable = false)
    private Integer sort;

    @Column(length = 150)
    private String icon;
}
