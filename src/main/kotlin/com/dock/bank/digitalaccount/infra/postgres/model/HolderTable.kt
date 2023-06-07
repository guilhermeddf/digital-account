package com.dock.bank.digitalaccount.infra.postgres.model


import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.UUID

@Table(name = "holders")
data class HolderTable (

    @Id
    val id: UUID,
    val cpf: String,
    val name: String
)