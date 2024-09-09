package com.microservices.user.users.models;

import com.microservices.user.passwords.models.PasswordEntity;
import com.microservices.user.roles.models.RoleEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

/**
 * Entity representing a user in the system.
 * This entity implements {@link UserDetails} for integration with Spring Security.
 *
 * <p>It contains personal information such as phone number, first name, last name, and security-related data such as
 * password and roles. Additionally, it tracks when the user was created and last modified.
 *
 * @see com.microservices.user.passwords.models.PasswordEntity
 * @see com.microservices.user.roles.models.RoleEntity
 * @see org.springframework.security.core.userdetails.UserDetails
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
@Table(name = "tb_users")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", columnDefinition = "uuid", unique = true, nullable = false, updatable = false)
    private UUID uid;

    /**
     * The user's phone number, which is unique across all users.
     */
    @Column(name = "phone", columnDefinition = "character varying", length = 12, nullable = false, unique = true)
    private String phone;

    /**
     * The password entity associated with the user.
     *
     * @see com.microservices.user.passwords.models.PasswordEntity
     */
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "password", nullable = false)
    private PasswordEntity passwordEntity;

    @Column(name = "first_name", columnDefinition = "character varying", length = 63, nullable = false)
    private String firstname;

    @Column(name = "last_name", columnDefinition = "character varying", length = 127, nullable = false)
    private String lastname;

    /**
     * Indicates whether the user's account is blocked or unblocked.
     */
    private boolean enabled;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "timestamp without time zone", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at", columnDefinition = "timestamp without time zone", insertable = false)
    private LocalDateTime modifiedAt;

    /**
     * The role associated with the user.
     *
     * @see com.microservices.user.roles.models.RoleEntity
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role", nullable = false)
    private RoleEntity role;

    /**
     * Retrieves the authorities granted to the user.
     *
     * @return A collection of {@link GrantedAuthority} representing the user's authorities.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    /**
     * Retrieves the username of the user, which in this case is the user's unique identifier (UUID).
     *
     * @return The user's UUID as a string.
     */
    @Override
    public String getUsername() {
        return uid.toString();
    }

    /**
     * Retrieves the user's password.
     *
     * @return The user's password.
     */
    @Override
    public String getPassword() {
        return passwordEntity.getPassword();
    }

    /**
     * Checks if the user's account is non-expired.
     *
     * @return {@code true} if the account is not expired, otherwise {@code false}.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Checks if the user's account is non-locked.
     *
     * @return {@code true} if the account is not locked, otherwise {@code false}.
     */
    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    /**
     * Checks if the user's credentials are non-expired.
     *
     * @return {@code true} if the credentials are not expired, otherwise {@code false}.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return passwordEntity.getExpiredAt().isAfter(LocalDateTime.now());
    }
}
