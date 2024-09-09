package com.microservices.warehouse.storages.models;

/**
 * This enum represents the different statuses of a storage, indicating its safety and report history.
 *
 * <ul>
 *   <li>{@code COMPLETELY_SAFE} - Indicates that the storage is completely safe without any reports.</li>
 *   <li>{@code SAFE_FOR_NOW} - Indicates that the storage is currently safe but has some reports in its history.</li>
 *   <li>{@code ON_REPORT_STASH} - Indicates that the storage is under review due to customer reports.</li>
 *   <li>{@code ON_BLOCK_STASH} - Indicates that the storage has been flagged as guilty of customer reports and is currently blocked.</li>
 * </ul>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */

public enum StorageStatusEnum {

    /**
    * This means the storage is completely safe without any report
    */
    COMPLETELY_SAFE,

    /**
     * This means the storage is safe with some report in history
     */
    SAFE_FOR_NOW,

    /**
     * This means the storage is reported and support team is checking reports now.
     */
    ON_REPORT_STASH,

    /**
     * This means the storage is guilty of customer's reports.
     */
    ON_BLOCK_STASH
}
