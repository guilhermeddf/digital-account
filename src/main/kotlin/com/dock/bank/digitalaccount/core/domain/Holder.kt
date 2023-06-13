package com.dock.bank.digitalaccount.core.domain

import com.dock.bank.digitalaccount.core.exceptions.DomainException
import com.dock.bank.digitalaccount.infra.rest.controllers.HolderController
import org.slf4j.LoggerFactory
import java.util.*

data class Holder (
    val id : UUID,
    val cpf: String,
    val name: String
) {

    companion object {
        private val logger = LoggerFactory.getLogger(HolderController::class.java)
    }

    fun validateCpf()  {
        if(!validate()) {
            throw DomainException(message = "Holder cpf is not valid.")
        }
    }

    private fun validate() : Boolean {
        logger.info("Validating CPF: ${this.cpf} from holder ${this.name}.")
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
