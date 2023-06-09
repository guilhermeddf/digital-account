package com.dock.bank.digitalaccount.infra.postgres.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.UUID

@Entity
@Table(name = "user", schema = "digital_account_service")
data class UserTable (

    @Id
    val id: UUID,

    @Column(name = "username", nullable = false)
    val username: String,

    @Column(name = "password", nullable = false)
    val password: String,

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    val role: Role
)