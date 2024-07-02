package org.hamsaye.storages.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "tb_storage_images")
public class StorageImageEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "storage_img_uid", columnDefinition = "uuid", unique = true, nullable = false)
    private UUID uid;

    @OneToOne
    @JoinColumn(name = "fk_storage_uid", unique = true, nullable = false)
    private StorageEntity storage;

    @Column(name = "image1", columnDefinition = "text", nullable = false)
    private String coverImage;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "timestamp", nullable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at", columnDefinition = "timestamp")
    private Timestamp modifiedAt;

    @Column(name = "image2", columnDefinition = "text")
    private String secondImage;

    @Column(name = "image3", columnDefinition = "text")
    private String thirdImage;

    @Column(name = "image4", columnDefinition = "text")
    private String forthImage;

    @Column(name = "image5", columnDefinition = "text")
    private String fifthImage;

    @Column(name = "image6", columnDefinition = "text")
    private String sixthImage;

    @Column(name = "image7", columnDefinition = "text")
    private String seventhImage;

}
