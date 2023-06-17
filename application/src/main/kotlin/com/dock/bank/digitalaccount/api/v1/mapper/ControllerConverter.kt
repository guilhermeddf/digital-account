package com.dock.bank.digitalaccount.api.v1.mapper

import com.dock.bank.digitalaccount.api.v1.request.CreateHolderRequest
import com.dock.bank.digitalaccount.api.v1.request.CreateLoginRequest
import com.dock.bank.digitalaccount.api.v1.request.CreateUserRequest
import com.dock.bank.digitalaccount.api.v1.response.*
import com.dock.bank.digitalaccount.core.domain.*
import java.util.*

fun CreateHolderRequest.toEntity(id: UUID = UUID.randomUUID()) : Holder {
    return Holder(
        id = id,
        cpf = this.cpf,
        name = this.name
    )
}



fun CreateUserRequest.toEntity(id: UUID = UUID.randomUUID()): User {
    return User(
        id = id,
        username = this.username,
        password = this.password,
        role = Role.valueOf(this.role)
    )
}

fun User.toCreateUserResponse() : CreateUserResponse {
    return CreateUserResponse(
        username = this.username,
        password = this.password,
        role = this.role
    )
}

fun CreateLoginRequest.toEntity(): Login {
    return Login(
        username = this.username,
        password = this.password,
        token = null
    )
}

fun Login.toResponse(): CreateLoginResponse {
    return CreateLoginResponse(username = this.username, token = this.token)
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
        cpf = this.holder.cpf,
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