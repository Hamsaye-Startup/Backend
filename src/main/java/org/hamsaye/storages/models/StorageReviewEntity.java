/*
package org.hamsaye.storages.models;

import jakarta.persistence.*;
import lombok.*;
import org.hamsaye.generals.models.BaseModel;

import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_storage_reviews")
public class StorageReviewEntity extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "review_uid", columnDefinition = "uuid", nullable = false, unique = true)
    private UUID uid;

    // TODO: generate writer_id as user

    @Column(name = "writer_comment", columnDefinition = "character varying", length = 1024, nullable = false)
    private String comment;

    // TODO: generate owner_id as user

    @Column(name = "owner_answered", columnDefinition = "character varying", length = 1024)
    private String answer;

    @Column(name = "rating", columnDefinition = "real", nullable = false)
    private Float rating;

    @Column(name = "edited", columnDefinition = "boolean", nullable = false)
    private Boolean edited;

    @Column(name = "has_been_answered", columnDefinition = "boolean", nullable = false)
    private Boolean answered;

    @ManyToOne
    private StorageEntity storage;

    @Column(name = "status", columnDefinition = "character varying", length = 32, nullable = false)
    private String status;
}
*/
