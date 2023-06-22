package com.dock.bank.digitalaccount.ports.spi.messaging

import com.dock.bank.digitalaccount.core.domain.Holder

interface HolderMessagingPort {
    suspend fun publish(holder: Holder)
}