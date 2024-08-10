package com.microservices.reservation.warehouse.models;

import com.microservices.reservation.installments.models.InstallmentEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_multi_reservation_warehouse")
public class MultiReservationEntity
        extends ReservationEntity implements Serializable {

    @Builder(builderMethodName = "singleBuilder")
    public MultiReservationEntity(UUID uid, UUID reservedBy, Long warehouse, UUID owner, LocalDate fromDate, LocalDate toDate, LocalDateTime createdAt, LocalDateTime modifiedAt, Set<InstallmentEntity> installments) {
        super(uid, reservedBy, warehouse, owner, fromDate, toDate, createdAt, modifiedAt);
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
