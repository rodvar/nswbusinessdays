package com.rodvar.nswbusinesdays.domain

import java.time.LocalDate
import java.time.Month

class FixedDateHolidayRule(val name : String, val month : Month, val day : Int) : HolidayRule {
    override fun isHoliday(date: LocalDate): Boolean {
        return date.month == this.month && date.dayOfMonth == day
    }
}