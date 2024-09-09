package com.microservices.warehouse.storages.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * This class represents an entity for storing comments related to a storage.
 * It includes information about the comment's author, content, score, and timestamp.
 * <p>
 * The comments are associated with a specific storage entity and can be enabled or disabled
 * for display purposes. Only comments with the `enabled` flag set to `true` are visible to customers.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_storage_comment")
@SequenceGenerator(name = "tb_storage_comment_seq", sequenceName = "tb_storage_comment_seq", initialValue = 1001, allocationSize = 1)
public class CommentEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tb_storage_comment_seq"
    )
    @Column(name = "comment_id", columnDefinition = "bigint", unique = true, nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "storage", unique = true, updatable = false, columnDefinition = "bigint", foreignKey = @ForeignKey(name = "fk_storage_comment"))
    private StorageEntity storage;

    @Column(name = "comment_by", columnDefinition = "uuid", nullable = false, updatable = false)
    private UUID commentBy;

    @Column(name = "score", columnDefinition = "double precision")
    private Float score;

    @Column(name = "content", columnDefinition = "character varying", nullable = false, updatable = false)
    private String content;

    @Column(name = "comment_at", columnDefinition = "timestamp without time zone", nullable = false, updatable = false)
    private LocalDateTime commentAt;

    /**
     * This flag is used for displaying comment.
     * Customers can only see the true values.
     */
    @Column(name = "enabled", columnDefinition = "boolean", nullable = false)
    private boolean enabled;
}
