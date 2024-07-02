package org.hamsaye.storages.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hamsaye.utils.functional.Functionality;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;
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
public class StorageCategoryEntity implements Serializable, Functionality {

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

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "timestamp", nullable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at", columnDefinition = "timestamp")
    private Timestamp modifiedAt;

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
