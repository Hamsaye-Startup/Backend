package com.microservices.warehouse.storages.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_storage_review")
@SequenceGenerator(name = "tb_storage_review_seq", sequenceName = "tb_storage_review_seq", initialValue = 1001, allocationSize = 3)
public class ReviewEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tb_storage_review_seq"
    )
    @Column(name = "review_id", columnDefinition = "bigint", unique = true, nullable = false, updatable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "storage", unique = true, updatable = false, columnDefinition = "bigint")
    private StorageEntity storage;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(
            name = "in_comment_reviews",
            joinColumns = @JoinColumn(name = "fk_review_id", referencedColumnName = "review_id"),
            inverseJoinColumns = @JoinColumn(name = "fk_comment_id", referencedColumnName = "comment_id")
    )
    private List<CommentEntity> comments;
}
