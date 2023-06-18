package com.dock.bank.digitalaccount.utils

fun String.mask(): String {
    return this.replaceAfter(this[2], "*".repeat(this.length - 3))
}