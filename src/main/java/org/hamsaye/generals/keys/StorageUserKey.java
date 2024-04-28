package org.hamsaye.generals.keys;

import jakarta.persistence.Embeddable;
import org.hamsaye.storages.models.StorageEntity;

import java.io.Serializable;

@Embeddable
public class StorageUserKey implements Serializable {

    private StorageEntity storage;

    // TODO: generate a reference to user
}
