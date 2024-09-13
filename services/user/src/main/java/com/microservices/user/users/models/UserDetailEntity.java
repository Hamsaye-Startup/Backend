package com.microservices.user.users.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Entity representing a user detail in the system.
 * Each user is associated with a unique user and has additional attributes such as
 * a national ID (NID), biography, gender, and loyalty status.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_user_detail")
@SequenceGenerator(name = "tb_user_detail_seq", sequenceName = "tb_user_detail_seq", allocationSize = 3)
public class UserDetailEntity implements Serializable {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tb_user_detail_seq"
    )
    @Column(name = "detail_id", columnDefinition = "bigint", unique = true, nullable = false, updatable = false)
    private Long id;

    @Column(name = "nid", columnDefinition = "character varying", length = 10, unique = true, nullable = false, updatable = false)
    private String nid;

    @OneToOne
    @JoinColumn(name = "user_id", columnDefinition = "uuid", unique = true, nullable = false, updatable = false)
    private UserEntity user;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "timestamp without time zone", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at", columnDefinition = "timestamp without time zone", insertable = false)
    private LocalDateTime modifiedAt;

    @Column(name = "bio", columnDefinition = "character varying", length = 255)
    private String bio;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", columnDefinition = "character varying", length = 31)
    private GenderEnum gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "loyalty", columnDefinition = "character varying", length = 31)
    private UserLoyaltyStatus loyalty;

    @Enumerated(EnumType.STRING)
    @Column(name = "connection", columnDefinition = "character varying", length = 31)
    private UserConnectionStatus connectionStatus;

    // TODO: Add support for profile image storage (e.g., using AWS S3)
}
