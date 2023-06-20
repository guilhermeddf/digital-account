package com.dock.bank.digitalaccount.postgresql.model

import com.dock.bank.digitalaccount.core.domain.Status
import jakarta.persistence.*
import java.math.BigInteger
import java.util.UUID

@Entity
@Table(name = "account")
data class AccountTable (

    @Id
    val id: UUID,

    @Column(name = "balance", nullable = false)
    val balance: BigInteger,

    @Column(name = "number", nullable = false)
    val number: String,

    @Column(name = "branch", nullable = false)
    val branch: String,

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "holder_id")
    val holder: HolderTable,

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    val status: Status,

    @Column(name = "withdrawal_limit", nullable = false)
    val withdrawalLimit: BigInteger
)