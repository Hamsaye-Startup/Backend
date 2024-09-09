package com.microservices.reservation.warehouse.models;

import com.microservices.reservation.installments.models.InstallmentEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * This class is an entity class for multi-reservation in the warehouse.
 * It extends the {@link ReservationEntity} class and adds support for multiple installments.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_multi_reservation_warehouse")
public class MultiReservationEntity
        extends ReservationEntity implements Serializable {

    @Builder(builderMethodName = "singleBuilder")
    public MultiReservationEntity(UUID uid, UUID reservedBy, Long warehouse, UUID owner, LocalDate fromDate, LocalDate toDate, ReservationStats stats, LocalDateTime createdAt, LocalDateTime modifiedAt, Set<InstallmentEntity> installments) {
        super(uid, reservedBy, warehouse, owner, fromDate, toDate, stats, createdAt, modifiedAt);
        this.installments = installments;
    }

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "in_installment_reservation_warehouse",
            joinColumns = @JoinColumn(name = "fk_reservation_id", referencedColumnName = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "fk_installment_id", referencedColumnName = "installment_id")
    )
    private Set<InstallmentEntity> installments;
}
