package com.microservices.warehouse.storages.models;

import com.microservices.warehouse.storages.models.keys.BookmarkId;
import com.microservices.warehouse.storages.models.keys.FavouritesId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "tb_storage_favourites")
public class FavouritesBookEntity implements Serializable {

    @EmbeddedId
    private FavouritesId id;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "timestamp without time zone", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
