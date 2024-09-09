package com.microservices.user.passwords.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Entity class representing a password in the system.
 * This class maps to the "tb_passwords" table and contains fields for password details, including the password value,
 * creation timestamp, and expiration timestamp.
 *
 * @see jakarta.persistence.Entity
 * @see jakarta.persistence.Table
 * @see jakarta.persistence.Column
 * @see jakarta.persistence.GeneratedValue
 * @see jakarta.persistence.GenerationType
 * @see org.hibernate.annotations.CreationTimestamp
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
@Table(name = "tb_passwords")
@SequenceGenerator(name = "tb_password_seq", sequenceName = "tb_password_seq", initialValue = 101, allocationSize = 8)
public class PasswordEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_password_seq")
    @Column(name = "password_id", unique = true, nullable = false, updatable = false)
    private Long id;

    @Column(name = "password", columnDefinition = "character varying", length = 1000, nullable = false)
    private String password;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "timestamp without time zone", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp when the password will expire.
     * This field must be set when creating or updating the password record.
     */
    @Column(name = "expired_at", columnDefinition = "timestamp without time zone", updatable = false, nullable = false)
    private LocalDateTime expiredAt;
}
