package com.microservices.user.passwords.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

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

    @Column(name = "expired_at", columnDefinition = "timestamp without time zone", updatable = false, nullable = false)
    private LocalDateTime expiredAt;
}
