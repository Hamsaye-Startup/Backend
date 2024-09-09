package com.microservices.warehouse.storages.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

/**
 * This class represents an embedded entity for storage's score. It includes the average score of the storage and the number of votes
 * (comments) used for calculating the total score.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Embeddable
public class Score implements Serializable {

    /**
     * This is an average of storage's scores
     */
    @Column(name = "avg_score", columnDefinition = "double precision", nullable = false)
    private Float score;

    /**
     * The number of comments named votes for counting total votes
     */
    @Column(name = "vote_number", columnDefinition = "integer", nullable = false)
    private Integer votes;
}
