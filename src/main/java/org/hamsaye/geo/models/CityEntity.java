package org.hamsaye.geo.models;

import jakarta.persistence.*;
import lombok.*;
import org.hamsaye.utils.functional.Functionality;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "tb_cities",
        indexes = {
                @Index(name = "storage_abv_index", columnList = "abv")
        }
)
public class CityEntity implements Serializable, Functionality {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uid;

    @Column(name = "name", columnDefinition = "character varying", length = 128, nullable = false)
    private String name;

    @Column(name = "abv", columnDefinition = "character", length = 6, nullable = false, unique = true)
    private String abbreviation;

    @Column(name = "active", columnDefinition = "boolean", nullable = false)
    private Boolean active;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "timestamp without time zone", nullable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at", columnDefinition = "timestamp without time zone")
    private Timestamp modifiedAt;
}
