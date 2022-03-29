package com.dock.bank.digitalaccount.utils

import kotlin.random.Random


fun cpfGenerator(): String {
    val radonNumber = Random(System.currentTimeMillis())

    val numbers = IntArray(9).map { radonNumber.nextInt(9) }

    val dv1 = ((0..8).sumOf { (it + 1) * numbers[it] }).rem(11).let {
        if (it >= 10) 0 else it
    }

    val dv2 = ((0..8).sumOf { it * numbers[it] }.let { (it + (dv1 * 9)).rem(11) }).let {
        if (it >= 10) 0 else it
    }

    return intArrayOf(*numbers.toIntArray(), dv1, dv2)
        .toList().toString()
        .replace("[", "")
        .replace("]", "")
        .replace(", ", "")
}
