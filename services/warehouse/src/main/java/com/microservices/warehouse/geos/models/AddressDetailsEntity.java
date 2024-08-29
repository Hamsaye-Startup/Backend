package com.microservices.warehouse.geos.models;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_address_details")
public class AddressDetailsEntity {

    @Id
    private String postalCode;

    private String country;

    private String province;

    private String county;

    private String city;

    private String region;

    private String neighborhood;

    private String primary;

    private String plaque;
}
