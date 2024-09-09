package com.microservices.warehouse.geos.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents an address entity in the system.
 * <p>
 * This entity contains information about an address including its main address, a compatible
 * address format, geographic coordinates, and timestamps for creation and modification. It also
 * has a one-to-one relationship with {@link AddressDetailsEntity} that provides detailed address
 * information unique by postal code.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "tbl_address")
public class AddressEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "address_id", columnDefinition = "uuid", unique = true, nullable = false)
    private UUID uid;

    @Column(name = "address", columnDefinition = "character varying", nullable = false)
    private String address;

    @Column(name = "address_compat", columnDefinition = "character varying", nullable = false)
    private String addressCompat;

    @Column(name = "coordinate", columnDefinition = "character varying", length = 127, nullable = false)
    private String coordinate;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "timestamp without time zone", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at", columnDefinition = "timestamp without time zone", insertable = false)
    private LocalDateTime modifiedAt;

    /**
     * Detailed address information unique by postal code.
     * <p>
     * This is a one-to-one relationship with {@link AddressDetailsEntity}.
     * </p>
     */
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "details", referencedColumnName = "postal_code", columnDefinition = "character varying")
    private AddressDetailsEntity details;
}
