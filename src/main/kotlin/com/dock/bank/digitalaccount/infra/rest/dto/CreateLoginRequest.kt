package com.dock.bank.digitalaccount.infra.rest.dto

import com.dock.bank.digitalaccount.infra.postgres.model.Role

data class CreateLoginRequest(val username: String, val password: String)
data class CreateUserRequest(val username: String, val password: String, val role: String)
data class CreateUserResponse(val username: String, val password: String, val role: Role)