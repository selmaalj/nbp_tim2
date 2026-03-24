package com.jobfair.domain.model;

import com.jobfair.domain.model.enums.MediaKind;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
        name = "media_outlets",
        schema = "NBPT2",
        uniqueConstraints = @UniqueConstraint(name = "uk_media_outlet_slug", columnNames = "slug"),
        indexes = {
                @Index(name = "idx_media_outlet_name", columnList = "name"),
                @Index(name = "idx_media_outlet_kind", columnList = "kind")
        }
)
public class MediaOutlet extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36)
    private String id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = false, length = 150)
    private String slug;

    @Column(length = 250)
    private String website;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private MediaKind kind;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "logo_id", foreignKey = @ForeignKey(name = "fk_media_outlet_logo"))
    private Media logo;

    @Builder.Default
    @OneToMany(mappedBy = "outlet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MediaParticipation> participations = new ArrayList<>();
}
