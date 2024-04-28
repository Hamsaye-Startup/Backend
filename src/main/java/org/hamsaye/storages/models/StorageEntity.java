package org.hamsaye.storages.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hamsaye.generals.models.BaseModel;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_storages")
public class StorageEntity extends BaseModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "storage_uid", columnDefinition = "uuid", unique = true, nullable = false)
    private UUID uid;

    @Column(name = "width", columnDefinition = "integer", nullable = false)
    private Integer width;

    @Column(name = "height", columnDefinition = "integer", nullable = false)
    private Integer height;

    @Column(name = "max_weight", columnDefinition = "integer")
    private Integer maxWeight;

    @Column(name = "amount", columnDefinition = "numeric", nullable = false)
    private Double amount;

    @Column(name = "discount_amt", columnDefinition = "double precision", nullable = false)
    private Double discountAmount;

    @Column(name = "description", columnDefinition = "character varying", length = 1024)
    private String description;

    @Column(name = "status", columnDefinition = "character varying", length = 32, nullable = false)
    private String status;

    @OneToOne
    @JoinColumn(name = "category_uid", columnDefinition = "uuid", nullable = false)
    private StorageCategoryEntity category;

    /*
    @OneToMany
    @JoinColumn(name = "", columnDefinition = "uuid", nullable = false)
    private List<StorageFeatureEntity> features;
    */

    // TODO: generate a relationship with user

    @Override
    public String toString() {
        return "Storage{" +
                "uid=" + uid +
                ", width=" + width +
                ", height=" + height +
                ", maxWeight=" + maxWeight +
                ", amount=" + amount +
                ", discountAmount=" + discountAmount +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
