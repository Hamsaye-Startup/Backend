package org.hamsaye.storages.models;

import jakarta.persistence.*;
import lombok.*;
import org.hamsaye.generals.models.BaseModel;

import java.io.Serializable;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_storage_comments")
public class StorageCommentEntity extends BaseModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id", columnDefinition = "bigint", nullable = false, unique = true)
    private Integer id;

    @Column(name = "comment", columnDefinition = "character varying", length = 1024)
    private String message;

    // TODO: generate the owner or writer of message as user

}
