package com.dock.bank.digitalaccount.infra.rest.converter

import com.dock.bank.digitalaccount.core.domain.Account
import com.dock.bank.digitalaccount.core.domain.Holder
import com.dock.bank.digitalaccount.core.domain.Transaction
import com.dock.bank.digitalaccount.infra.rest.dto.CreateAccountResponse
import com.dock.bank.digitalaccount.infra.rest.dto.CreateHolderRequest
import com.dock.bank.digitalaccount.infra.rest.dto.CreateHolderResponse
import com.dock.bank.digitalaccount.infra.rest.dto.CreateTransactionRequest
import com.dock.bank.digitalaccount.infra.rest.dto.CreateTransactionResponse
import com.dock.bank.digitalaccount.infra.rest.dto.GetAccountResponse
import com.dock.bank.digitalaccount.infra.rest.dto.RetrieveTransactionResponse
import java.util.UUID

fun CreateHolderRequest.toEntity(id: UUID = UUID.randomUUID()) : Holder {
    return Holder(
        id = id,
        cpf = this.cpf,
        name = this.name
    )
}

fun Holder.toCreateResponse() : CreateHolderResponse {
    return CreateHolderResponse(
        id = this.id,
        cpf = this.cpf,
        name = this.name
    )
}

fun Account.toCreateResponse() : CreateAccountResponse {
    return CreateAccountResponse(
        id = this.id,
        number = this.number,
        branch = this.branch,
        holderName = this.holder.name,
        status = this.status
    )
}

fun Account.toGetResponse() : GetAccountResponse {
    return GetAccountResponse(
        balance = balance,
        number = number,
        branch = branch
    )
}

fun Transaction.toCreateResponse() : CreateTransactionResponse {
    return CreateTransactionResponse(
        id = this.id,
        amount = this.amount,
        type = this.type,
        createdDate = this.createdDate
    )
}

fun Transaction.toRetrieveResponse() : RetrieveTransactionResponse {
    return RetrieveTransactionResponse(
        this.id,
        this.amount,
        this.type,
        this.createdDate
    )
}