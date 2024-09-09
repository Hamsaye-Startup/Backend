package com.microservices.warehouse.geos.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Represents detailed address information in the system.
 * <p>
 * This entity contains various components of an address, including country, province, county,
 * city, region, neighborhood, primary road, and plaque. It is uniquely identified by the postal code.
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
@Table(name = "tbl_address_details")
@SequenceGenerator(name = "tbl_address_details_seq", sequenceName = "tbl_address_details_seq", allocationSize = 4, initialValue = 3100)
public class AddressDetailsEntity implements Serializable {

    @Id
    @Column(name = "postal_code", columnDefinition = "character varying", length = 15, unique = true, nullable = false)
    private String postalCode;

    @Column(name = "country", columnDefinition = "character varying", length = 63)
    private String country;

    @Column(name = "province", columnDefinition = "character varying", length = 63)
    private String province;

    @Column(name = "county", columnDefinition = "character varying", length = 63)
    private String county;

    @Column(name = "city", columnDefinition = "character varying", length = 63)
    private String city;

    @Column(name = "region", columnDefinition = "character varying", length = 63)
    private String region;

    @Column(name = "neighborhood", columnDefinition = "character varying", length = 127)
    private String neighborhood;

    @Column(name = "primary_road", columnDefinition = "character varying", length = 127)
    private String primary;

    @Column(name = "plaque", columnDefinition = "character varying", length = 31)
    private String plaque;
}
