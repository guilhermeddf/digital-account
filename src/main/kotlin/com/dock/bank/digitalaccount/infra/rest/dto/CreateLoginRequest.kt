package com.dock.bank.digitalaccount.infra.rest.dto

import com.dock.bank.digitalaccount.infra.postgres.model.Role

data class CreateLoginRequest(val username: String, val password: String)
data class CreateRegisterRequest(val username: String, val password: String, val role: Role)