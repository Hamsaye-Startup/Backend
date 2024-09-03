package com.microservices.warehouse.storages.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Embeddable
public class Score implements Serializable {

    @Column(name = "avg_score", columnDefinition = "double precision", nullable = false)
    private Float score;

    @Column(name = "vote_number", columnDefinition = "integer", nullable = false)
    private Integer votes;
}
