package com.dock.bank.digitalaccount.infra.rest.dto

import java.util.UUID

data class CreateHolderRequest(
    val cpf: String,
    val name: String
)

data class CreateHolderResponse(
    val id: UUID,
    val cpf: String,
    val name: String
)