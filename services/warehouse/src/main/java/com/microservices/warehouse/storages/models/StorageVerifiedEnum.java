package com.microservices.warehouse.storages.models;

/**
 * This enum represents the verification status of a storage.
 * <ul>
 *   <li>{@code VERIFIED} - Indicates that the storage has been verified and meets the required standards.</li>
 *   <li>{@code NOT_VERIFIED} - Indicates that the storage has not been verified and may require further review.</li>
 * </ul>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */

public enum StorageVerifiedEnum {
    VERIFIED,
    NOT_VERIFIED
}
