package com.microservices.reservation.products.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Entity representing a type of product that can be stored in reserved storage.
 * This class is used to define and manage various product types, including hierarchical relationships
 * between different types of products.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_product_type")
@SequenceGenerator(name = "tb_product_type_seq", sequenceName = "tb_product_type_seq", allocationSize = 4, initialValue = 4001)
public class ProductTypeEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tb_product_type_seq"
    )
    @Column(name = "product_type_id", columnDefinition = "bigint", unique = true, nullable = false, updatable = false)
    private Long id;

    @Column(name = "name", columnDefinition = "character varying", length = 127, nullable = false)
    private String name;

    /**
     * Indicates whether the product type is active.
     */
    @Column(name = "active", columnDefinition = "boolean", nullable = false)
    private boolean active;

    /**
     * List of child product types associated with this product type.
     * Represents a hierarchical relationship where this type can have subtypes.
     */
    @OneToMany
    @JoinTable(
            name = "in_product_type",
            joinColumns = @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "fk_parent_id_product_type")),
            inverseJoinColumns = @JoinColumn(name = "child_id", foreignKey = @ForeignKey(name = "fk_child_id_product_type"))
    )
    private List<ProductTypeEntity> children;

    /**
     * Parent product type of this product type.
     * Represents a hierarchical relationship where this type is a subtype of another type.
     */
    @ManyToOne
    @JoinColumn(name = "parent", foreignKey = @ForeignKey(name = "fk_parent_product_type"))
    private ProductTypeEntity parent;
}
