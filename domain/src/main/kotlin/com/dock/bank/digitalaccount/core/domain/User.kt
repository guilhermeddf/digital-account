package com.dock.bank.digitalaccount.core.domain

import com.dock.bank.digitalaccount.infra.postgres.model.Role
import java.util.UUID

data class User (val id: UUID, val username: String, val password: String, val role: Role)