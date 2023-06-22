package com.dock.bank.digitalaccount.ports.spi.messaging

interface HolderReceiver {
    fun receive(): List<String>
}