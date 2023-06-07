package com.dock.bank.digitalaccount.infra.postgres.model

import com.dock.bank.digitalaccount.core.domain.TransactionType
import jakarta.persistence.*
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.math.BigInteger
import java.time.OffsetDateTime
import java.util.*



@Table(name = "transaction")
data class TransactionTable (

    @Id
    val id: UUID,
    val amount: BigInteger,
    @Enumerated(EnumType.STRING)
    val type: TransactionType,
    val createdDate: OffsetDateTime,
    val accountId: UUID
)