package org.hamsaye.storages.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@Table(name = "tb_storage_features")
public class StorageFeatureEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "feature_uid", columnDefinition = "uuid", nullable = false, unique = true)
    private UUID uid;

    @Column(name = "title", columnDefinition = "character varying", length = 64, nullable = false)
    private String title;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "timestamp", nullable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at", columnDefinition = "timestamp")
    private Timestamp modifiedAt;

    @Column(name = "description", columnDefinition = "character varying", length = 1024)
    private String description;

    @Column(name = "icon_image", columnDefinition = "text")
    private String icon;

    @Column(name = "has_icon_image", columnDefinition = "boolean", nullable = false)
    private Boolean hasIcon;

}
