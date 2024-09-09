package com.microservices.reservation.warehouse.models;

import com.microservices.reservation.installments.models.InstallmentEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * This class represents a single reservation entity in the warehouse system.
 * It extends {@link ReservationEntity} and adds a specific installment entity associated with the reservation.
 *
 * <p>This class is mapped to the "tb_single_reservation_warehouse" table. It includes all fields from the
 * parent {@link ReservationEntity} and adds an additional field for the installment entity.</p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_single_reservation_warehouse")
public class SingleReservationEntity
        extends ReservationEntity implements Serializable {

    @Builder(builderMethodName = "singleBuilder")
    public SingleReservationEntity(UUID uid, UUID reservedBy, Long warehouse, UUID owner, LocalDate fromDate, LocalDate toDate, ReservationStats stats, LocalDateTime createdAt, LocalDateTime modifiedAt, InstallmentEntity installment) {
        super(uid, reservedBy, warehouse, owner, fromDate, toDate, stats, createdAt, modifiedAt);
        this.installment = installment;
    }

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "installment", unique = true, nullable = false, columnDefinition = "uuid", foreignKey = @ForeignKey(name = "fk_installment_single_w_r"))
    private InstallmentEntity installment;
}
