package org.hamsaye.geo.models;


import jakarta.persistence.*;
import lombok.*;
import org.hamsaye.storages.models.StorageEntity;
import org.hamsaye.utils.functional.Functionality;

import java.io.Serializable;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_storage_locations")
@SequenceGenerator(name = "seq_storage_locations", sequenceName = "seq_storage_location", allocationSize = 1, initialValue = 101)
public class StorageLocationEntity implements Serializable, Functionality {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_storage_locations"
    )
    private Long id;

    @Column(name = "address", columnDefinition = "character varying", length = 1024, nullable = false)
    private String address;

    @Column(name = "geo_point", columnDefinition = "point", nullable = false)
    private String gp;

    @OneToOne
    @JoinColumn(name = "fk_storage_uid", columnDefinition = "uuid", nullable = false)
    private StorageEntity storage;

    @ManyToOne
    @JoinColumn(name = "fk_city_uid", columnDefinition = "uuid", nullable = false)
    private CityEntity city;
}
