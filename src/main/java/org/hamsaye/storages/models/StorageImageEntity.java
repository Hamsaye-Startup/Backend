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
@Table(name = "tb_storage_images")
public class StorageImageEntity extends BaseModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "storage_img_uid", columnDefinition = "uuid", unique = true, nullable = false)
    private UUID uid;

    @OneToOne
    @JoinColumn(name = "storage_uid", unique = true, nullable = false)
    private StorageEntity storage;

    @Column(name = "image1", columnDefinition = "text", nullable = false)
    private String coverImage;

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
