package com.paintingdiary.backend.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
@Entity
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private UserCharacter character;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private int progress;

    @Column(nullable = false)
    private Instant createDt;
    @Column(nullable = false)
    private Instant updateDt;

    @PrePersist
    void preInsert() {
        Instant now = Instant.now();
        if (Objects.isNull(this.createDt)) {
            this.createDt = now;
        }
        if (Objects.isNull(this.updateDt)) {
            this.updateDt = now;
        }
    }

    @PreUpdate
    void preUpdate() {
        this.updateDt = Instant.now();
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Skill other)) {
            return false;
        }

        boolean isSameTitle = ObjectUtils.nullSafeEquals(title, other.getTitle());
        boolean isSameDescription = ObjectUtils.nullSafeEquals(description, other.getDescription());

        return isSameTitle && isSameDescription;
    }
}

