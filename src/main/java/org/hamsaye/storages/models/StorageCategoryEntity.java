package org.hamsaye.storages.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "tb_storage_categories",
        indexes = {
                @Index(name = "storage_category_code_index", columnList = "code", unique = true)
        }
)
public class StorageCategoryEntity extends BaseModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "category_uid", columnDefinition = "uuid", unique = true, nullable = false)
    private UUID uid;

    @OneToOne
    @JoinColumn(name = "parent_uid", columnDefinition = "uuid")
    private StorageCategoryEntity parent;

    @Column(name = "name", columnDefinition = "character varying", length = 128, nullable = false)
    private String name;

    @Column(name = "code", columnDefinition = "character varying", length = 8, nullable = false)
    private String code;

    @Override
    public String toString() {
        return "StorageCategoryEntity{" +
                "uid=" + uid +
                ", parent=" + parent +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
