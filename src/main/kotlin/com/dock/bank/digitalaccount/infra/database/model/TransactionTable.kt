package com.dock.bank.digitalaccount.infra.database.model

import com.dock.bank.digitalaccount.core.domain.TransactionType
import java.math.BigInteger
import java.time.OffsetDateTime
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

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