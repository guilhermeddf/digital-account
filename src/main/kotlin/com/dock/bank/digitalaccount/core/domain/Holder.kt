package com.dock.bank.digitalaccount.core.domain

import com.dock.bank.digitalaccount.core.exceptions.DomainException
import java.util.UUID

data class Holder (
    val id : UUID,
    val cpf: String,
    val name: String
) {

    fun validateCpf()  {
        if(!validate()) {
            throw DomainException(message = "Holder cpf is not valid.")
        }
    }

    private fun validate() : Boolean {
        if (cpf.isEmpty()) return false

        val numbers = cpf.filter { it.isDigit() }.map {
            it.toString().toInt()
        }

        if (numbers.size != 11) return false

        if (numbers.all { it == numbers[0] }) return false

        val dv1 = ((0..8).sumOf { (it + 1) * numbers[it] }).rem(11).let {
            if (it >= 10) 0 else it
        }

        val dv2 = ((0..8).sumOf { it * numbers[it] }.let { (it + (dv1 * 9)).rem(11) }).let {
            if (it >= 10) 0 else it
        }

        return numbers[9] == dv1 && numbers[10] == dv2
    }
}
