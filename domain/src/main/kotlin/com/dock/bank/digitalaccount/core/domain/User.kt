package com.dock.bank.digitalaccount.core.domain

import java.util.*

data class User (val id: UUID, val username: String, val password: String, val role: Role)