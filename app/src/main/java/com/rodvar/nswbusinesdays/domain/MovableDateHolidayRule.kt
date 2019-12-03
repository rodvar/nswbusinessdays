package com.rodvar.nswbusinesdays.domain

import java.time.DayOfWeek
import java.time.LocalDate

class MovableDateHolidayRule(val name : String, private val monthValue : Int, private val dayOfMonth : Int) : HolidayRule {
    override fun isHoliday(date: LocalDate): Boolean {
        return LocalDate.parse("${date.year}-${toDateString(monthValue)}-${toDateString(dayOfMonth)}").let {
            when (it.dayOfWeek) {
                DayOfWeek.SUNDAY -> date.monthValue == this.monthValue && date.dayOfMonth == dayOfMonth + 1
                DayOfWeek.SATURDAY -> date.monthValue == this.monthValue && date.dayOfMonth == dayOfMonth + 2
                else -> date.monthValue == this.monthValue && date.dayOfMonth == dayOfMonth
            }
        }
    }

    private fun toDateString(dateValue: Int): String {
        return if (dateValue < 10)
            "0$dateValue"
        else
            dateValue.toString()
    }
}