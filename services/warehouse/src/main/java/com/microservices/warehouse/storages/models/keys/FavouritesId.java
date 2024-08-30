package com.microservices.warehouse.storages.models.keys;

import com.microservices.warehouse.storages.models.StorageEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class FavouritesId implements Serializable {

    @Column(name = "user_id")
    private UUID userId;

    @OneToOne
    @JoinColumn(name = "storage_id")
    private StorageEntity storage;
}
