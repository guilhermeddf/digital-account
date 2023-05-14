package com.dock.bank.digitalaccount.infra.postgres.model

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

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