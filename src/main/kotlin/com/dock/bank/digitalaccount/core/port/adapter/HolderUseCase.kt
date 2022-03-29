package com.dock.bank.digitalaccount.core.port.adapter

import com.dock.bank.digitalaccount.core.domain.Holder
import java.util.UUID

interface HolderUseCase {
    suspend fun create(holder: Holder) : Holder
    suspend fun delete(id: UUID)
}