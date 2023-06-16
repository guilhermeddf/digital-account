package com.dock.bank.digitalaccount.core.exception

import java.time.OffsetDateTime

data class ErrorMessage (
    val timestamp: OffsetDateTime,
    val error: String,
    val message: String?,
    val status: Int
)