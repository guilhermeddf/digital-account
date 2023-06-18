package com.dock.bank.digitalaccount.postgresql.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "holder")
data class HolderTable (

    @Id
    val id: UUID,

    @Column(name = "cpf", nullable = false)
    val cpf: String,

    @Column(name = "name", nullable = false)
    val name: String
)