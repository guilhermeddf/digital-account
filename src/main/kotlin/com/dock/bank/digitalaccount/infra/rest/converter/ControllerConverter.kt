package com.dock.bank.digitalaccount.infra.rest.converter

//import com.dock.bank.digitalaccount.utils.CustomUserDetails
import com.dock.bank.digitalaccount.core.domain.*
import com.dock.bank.digitalaccount.infra.rest.dto.*
import java.util.*

fun CreateHolderRequest.toEntity(id: UUID = UUID.randomUUID()) : Holder {
    return Holder(
        id = id,
        cpf = this.cpf,
        name = this.name
    )
}

fun CreateRegisterRequest.toEntity(id: UUID = UUID.randomUUID()): User {
    return User(
        id = id,
        username = this.username,
        password = this.password,
        role = this.role
    )
}

fun CreateLoginRequest.toEntity(): Login {
    return Login(
        username = this.username,
        password = this.password
    )
}

//fun CustomUserDetails.toTable(): UserTable {
//    return UserTable(
//        id = this.getId(),
//        username = this.username,
//        password = this.password,
//        role = Role.valueOf(this.authorities.stream().findFirst().get().authority))
//}

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