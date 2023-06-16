package com.dock.bank.digitalaccount.infra.postgres.model

import com.dock.bank.digitalaccount.core.domain.TransactionType
import jakarta.persistence.*
import java.math.BigInteger
import java.time.OffsetDateTime
import java.util.*


@Entity
@Table(name = "transaction")
data class TransactionTable (

    @Id
    val id: UUID,

    @Column(name = "amount", nullable = false)
    val amount: BigInteger,

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    val type: TransactionType,

    @Column(name = "created_date", nullable = false)
    val createdDate: OffsetDateTime,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id")
    val account: AccountTable
)