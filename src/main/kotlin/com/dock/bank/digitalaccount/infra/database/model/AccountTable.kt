package com.dock.bank.digitalaccount.infra.database.model

import com.dock.bank.digitalaccount.core.domain.Status
import java.math.BigInteger
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.OneToOne
import javax.persistence.Table

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