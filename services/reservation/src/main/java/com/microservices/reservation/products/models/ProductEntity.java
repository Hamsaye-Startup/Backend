package com.microservices.reservation.products.models;

import com.microservices.reservation.warehouse.models.ReservationEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_product")
@SequenceGenerator(name = "tb_product_seq", sequenceName = "tb_product_seq", allocationSize = 4, initialValue = 10001)
public class ProductEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tb_product_seq"
    )
    @Column(name = "product_id", columnDefinition = "bigint", unique = true, nullable = false, updatable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_type", nullable = false, foreignKey = @ForeignKey(name = "fk_type_product"))
    private ProductTypeEntity type;

    @Column(name = "price", columnDefinition = "numeric", nullable = false)
    private Double price;

    @Column(name = "description", columnDefinition = "character varying", length = 511, nullable = false)
    private String desc;

    @ManyToOne
    @JoinColumn(name = "reservation", nullable = false, foreignKey = @ForeignKey(name = "fk_reservation_product"))
    private ReservationEntity reservation;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "timestamp without time zone", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
