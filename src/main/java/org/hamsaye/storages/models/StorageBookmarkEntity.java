/*
package org.hamsaye.storages.models;

import jakarta.persistence.*;
import lombok.*;
import org.hamsaye.generals.keys.StorageUserKey;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_bookmarks")
public class StorageBookmarkEntity implements Serializable {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(
                    name = "storage",
                    column = @Column(name = "storage_uid", columnDefinition = "uuid")
            )
            // TODO: override the user
    })
    private StorageUserKey key;

    @CreationTimestamp
    private Timestamp createdAt;
}
*/
