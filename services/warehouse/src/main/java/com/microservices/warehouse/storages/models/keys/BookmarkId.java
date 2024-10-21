package com.microservices.warehouse.storages.models.keys;

import com.microservices.warehouse.storages.models.StorageEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

/**
 * Represents the composite key for the {@link com.microservices.warehouse.storages.models.BookmarkEntity} entity.
 * <p>
 * This class is used as an embedded ID to uniquely identify a bookmark entry associated with a specific user and storage entity.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Embeddable
public class BookmarkId implements Serializable {

    @Column(name = "user_id")
    private UUID userId;

    @OneToOne
    @JoinColumn(name = "storage_id")
    private StorageEntity storage;
}
