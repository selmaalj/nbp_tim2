package com.jobfair.domain.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
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
        name = "jobs",
        schema = "NBPT2",
        uniqueConstraints = @UniqueConstraint(name = "uk_job_slug", columnNames = "slug"),
        indexes = @Index(name = "idx_job_title", columnList = "title")
)
public class Job extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36)
    private String id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, length = 200)
    private String slug;

    @Column(nullable = false, columnDefinition = "CLOB")
    private String description;

    @Column(name = "apply_url", length = 1024)
    private String applyUrl;

    @Column(name = "apply_email", length = 160)
    private String applyEmail;

    @Column(name = "posted_at", nullable = false)
    private LocalDateTime postedAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false, foreignKey = @ForeignKey(name = "fk_job_organization"))
    private Organization organization;

    @Builder.Default
    @ElementCollection
    @CollectionTable(
            name = "job_tags",
            schema = "NBPT2",
            joinColumns = @JoinColumn(name = "job_id", foreignKey = @ForeignKey(name = "fk_job_tag_job"))
    )
    @Column(name = "tag_value", length = 100, nullable = false)
    private Set<String> tags = new LinkedHashSet<>();

    @PrePersist
    public void prePersist() {
        if (postedAt == null) {
            postedAt = LocalDateTime.now();
        }
    }
}
