package com.microservices.warehouse.warehouses.models.keys;

import com.microservices.warehouse.warehouses.models.WarehouseEntity;
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
public class BookmarkId implements Serializable {

    private UUID uid;

    @OneToOne
    @JoinColumn(name = "warehouse_id")
    private WarehouseEntity warehouse;
}
