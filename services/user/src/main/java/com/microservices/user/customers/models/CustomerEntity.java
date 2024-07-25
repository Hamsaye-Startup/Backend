package com.microservices.user.customers.models;

import com.microservices.user.users.models.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_customers")
public class CustomerEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "customer_id", columnDefinition = "uuid", unique = true, nullable = false, updatable = false)
    private UUID uid;

    @Column(name = "nid", columnDefinition = "character varying", length = 10, unique = true, nullable = false, updatable = false)
    private String nid;

    @OneToOne
    @JoinColumn(name = "user_id", columnDefinition = "uuid", unique = true, nullable = false, updatable = false)
    private UserEntity user;

    @Column(name = "status", columnDefinition = "character varying", length = 31, nullable = false)
    private String status;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "timestamp without time zone", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at", columnDefinition = "timestamp without time zone", insertable = false)
    private LocalDateTime modifiedAt;

    @Column(name = "bio", columnDefinition = "character varying", length = 255)
    private String bio;

    // todo profile image --> AWS(s3)
}
