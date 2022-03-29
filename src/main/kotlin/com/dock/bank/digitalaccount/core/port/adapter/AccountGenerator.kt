package com.dock.bank.digitalaccount.core.port.adapter

import com.dock.bank.digitalaccount.core.domain.Account
import com.dock.bank.digitalaccount.core.domain.Holder


interface AccountGenerator {
  fun generateAccount(holder: Holder) : Account
}