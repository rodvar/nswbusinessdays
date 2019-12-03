package com.rodvar.nswbusinesdays.domain

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month

class OcurrenceDateHolidayRule(val name: String, private val ocurrence: Int, private val dayOfWeek: DayOfWeek, private val month: Month) : HolidayRule {

    override fun isHoliday(date: LocalDate): Boolean {
        if (date.month != this.month || date.dayOfWeek != this.dayOfWeek)
            return false
        return (date.dayOfMonth % 7 == (this.ocurrence - 1))
    }

}