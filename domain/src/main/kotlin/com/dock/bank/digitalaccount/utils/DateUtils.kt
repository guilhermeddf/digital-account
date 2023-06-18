package com.dock.bank.digitalaccount.utils

import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZoneOffset

object DateUtils {

    fun getStartDate(
        date: LocalDate
    ): OffsetDateTime = OffsetDateTime.of(
        date.year,
        date.month.value,
        date.dayOfMonth,
        0,
        0,
        0,
        0,
        ZoneOffset.ofHours(-3)
    )

    fun getFinishDate(
        date: LocalDate
    ) : OffsetDateTime = OffsetDateTime.of(
        date.year,
        date.month.value,
        date.dayOfMonth,
        23,
        59,
        59,
        0,
        ZoneOffset.ofHours(-3)
    )

    fun getLocalDate(
        date: OffsetDateTime
    ) : LocalDate = LocalDate.of(
        date.year,
        date.month.value,
        date.dayOfMonth
    )
}