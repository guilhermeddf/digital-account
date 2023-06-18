package com.dock.bank.digitalaccount.postgresql.model

import com.dock.bank.digitalaccount.core.domain.Role
import jakarta.persistence.*
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