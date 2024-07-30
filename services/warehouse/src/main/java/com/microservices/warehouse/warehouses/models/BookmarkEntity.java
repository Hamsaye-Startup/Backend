package com.microservices.warehouse.warehouses.models;

import com.microservices.warehouse.warehouses.models.keys.BookmarkId;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
