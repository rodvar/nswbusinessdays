package com.rodvar.nswbusinesdays.domain

import com.rodvar.nswbusinesdays.DateUtils
import java.time.DayOfWeek
import java.time.LocalDate

class MovableDateHolidayRule(val name : String, private val monthValue : Int, private val dayOfMonth : Int) : HolidayRule {
    override fun isHoliday(date: LocalDate): Boolean {
        return LocalDate.parse("${date.year}-${DateUtils.toDateString(monthValue)}-${DateUtils.toDateString(dayOfMonth)}").let {
            when (it.dayOfWeek) {
                DayOfWeek.SUNDAY -> date.monthValue == this.monthValue && date.dayOfMonth == dayOfMonth + 1
                DayOfWeek.SATURDAY -> date.monthValue == this.monthValue && date.dayOfMonth == dayOfMonth + 2
                else -> date.monthValue == this.monthValue && date.dayOfMonth == dayOfMonth
            }
        }
    }
}