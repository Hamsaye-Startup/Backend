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

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_storage_comments")
public class StorageCommentEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id", columnDefinition = "bigint", nullable = false, unique = true)
    private Integer id;

    @Column(name = "comment", columnDefinition = "character varying", length = 1024)
    private String message;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "timestamp", nullable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at", columnDefinition = "timestamp")
    private Timestamp modifiedAt;

    // TODO: generate the owner or writer of message as user

}
