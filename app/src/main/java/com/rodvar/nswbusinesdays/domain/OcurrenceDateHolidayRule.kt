package com.rodvar.nswbusinesdays.domain

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month

class OcurrenceDateHolidayRule(val name: String, private val ocurrence: Int, private val dayOfWeek: DayOfWeek, private val month: Month) : HolidayRule {

    companion object {
        const val DAYS_IN_WEEK = 7
    }

    override fun isHoliday(date: LocalDate): Boolean {
        if (date.month != this.month || date.dayOfWeek != this.dayOfWeek)
            return false
        return (date.dayOfMonth / DAYS_IN_WEEK == (this.ocurrence - 1))
    }

}