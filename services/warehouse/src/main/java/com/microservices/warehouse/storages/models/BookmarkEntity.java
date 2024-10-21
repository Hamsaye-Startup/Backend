package com.microservices.warehouse.storages.models;

import com.microservices.warehouse.storages.models.keys.BookmarkId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents an entity for storing bookmarks related to storage items.
 * <p>
 * This class maps to the "tb_storage_bookmark" table and contains an embedded ID composed of a user ID and a storage ID,
 * along with a timestamp indicating when the bookmark was created.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "tb_storage_bookmark")
public class BookmarkEntity implements Serializable {

    @EmbeddedId
    private BookmarkId id;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "timestamp without time zone", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
