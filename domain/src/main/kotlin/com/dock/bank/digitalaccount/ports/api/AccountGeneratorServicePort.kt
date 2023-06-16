package com.dock.bank.digitalaccount.ports.api

import com.dock.bank.digitalaccount.core.domain.Account
import com.dock.bank.digitalaccount.core.domain.Holder


interface AccountGeneratorServicePort {
  fun generateAccount(holder: Holder) : Account
}