package com.jobfair.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
        name = "committees",
        schema = "NBPT2",
        uniqueConstraints = @UniqueConstraint(name = "uk_committees_year", columnNames = "year")
)
public class Committee extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36)
    private String id;

    @Column(nullable = false)
    private Integer year;

    @Column(length = 150)
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "committee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommitteeMember> members = new ArrayList<>();
}
