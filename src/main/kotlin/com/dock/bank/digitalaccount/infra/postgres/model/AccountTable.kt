package com.dock.bank.digitalaccount.infra.postgres.model

import com.dock.bank.digitalaccount.core.domain.Status
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.math.BigInteger
import java.util.*


@Table(name = "accounts")
data class AccountTable (

    @Id
    val id: UUID,

    val balance: BigInteger,

    val number: String,

    val branch: String,

    val holderId: UUID,

    val status: Status,

    val withdrawalLimit: BigInteger
)