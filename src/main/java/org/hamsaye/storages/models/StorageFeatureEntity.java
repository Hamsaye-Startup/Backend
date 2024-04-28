package org.hamsaye.storages.models;

import jakarta.persistence.*;
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
@Table(name = "tb_storage_features")
public class StorageFeatureEntity extends BaseModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "feature_uid", columnDefinition = "uuid", nullable = false, unique = true)
    private UUID uid;

    @Column(name = "title", columnDefinition = "character varying", length = 64, nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "character varying", length = 1024)
    private String description;

    @Column(name = "icon_image", columnDefinition = "text")
    private String icon;

    @Column(name = "has_icon_image", columnDefinition = "boolean", nullable = false)
    private Boolean hasIcon;

    @Override
    public String toString() {
        return "StorageFeatureEntity{" +
                "uid=" + uid +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", icon='" + icon + '\'' +
                ", hasIcon=" + hasIcon +
                '}';
    }
}
