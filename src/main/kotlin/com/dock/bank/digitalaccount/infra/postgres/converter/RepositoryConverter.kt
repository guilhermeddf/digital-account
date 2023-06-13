package com.dock.bank.digitalaccount.infra.postgres.converter

import com.dock.bank.digitalaccount.core.domain.Account
import com.dock.bank.digitalaccount.core.domain.Holder
import com.dock.bank.digitalaccount.core.domain.Transaction
import com.dock.bank.digitalaccount.core.domain.User
import com.dock.bank.digitalaccount.infra.postgres.model.*
import com.dock.bank.digitalaccount.utils.CustomUserDetails

fun AccountTable.toEntity() : Account {
    return Account(
        this.id,
        this.balance,
        this.number,
        this.branch,
        this.holder.toEntity(),
        this.status,
        this.withdrawalLimit
    )
}

fun Account.toTable() : AccountTable {
    return AccountTable(
        this.id,
        this.balance,
        this.number,
        this.branch,
        this.holder.toTable(),
        this.status,
        this.withdrawalLimit
    )
}

fun UserTable.toEntity(): User {
    return User(id = this.id,
        username = this. username,
        password = this.password,
        role = this.role
    )
}

fun User.toTable(): UserTable {
    return UserTable(id = this.id,
        username = this.username,
        password = this.password,
        role = this.role
    )
}

fun CustomUserDetails.toTable(): UserTable {
    return UserTable(
        id = this.getId(),
        username = this.username,
        password = this.password,
        role = Role.valueOf(this.authorities.stream().findFirst().get().authority))
}

fun Holder.toTable() : HolderTable {
    return HolderTable(
        this.id,
        this.cpf,
        this.name
    )
}

fun HolderTable.toEntity() : Holder {
    return Holder(
        this.id,
        this.cpf,
        this.name
    )
}

fun Transaction.toTable() : TransactionTable {
    return TransactionTable(
        this.id,
        this.amount,
        this.type,
        this.createdDate,
        this.account.toTable()
    )
}

fun TransactionTable.toEntity() : Transaction {
    return Transaction(
        this.id,
        this.amount,
        this.type,
        this.createdDate,
        this.account.toEntity()
    )
}