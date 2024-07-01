package org.hamsaye.storages.status;

public class StorageStatusGenerator {

    public static StorageStatus generateNewStorage() {
        return new StorageStatus(
                false,
                true
        );
    }
}
