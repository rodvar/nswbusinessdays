package com.rodvar.nswbusinesdays

import com.rodvar.nswbusinesdays.business.HolidaysDeterminator
import com.rodvar.nswbusinesdays.domain.FixedDateHolidayRule
import com.rodvar.nswbusinesdays.domain.HolidayRule
import com.rodvar.nswbusinesdays.domain.MovableDateHolidayRule
import com.rodvar.nswbusinesdays.domain.OcurrenceDateHolidayRule
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month

class NSWHolidayDeterminator : HolidaysDeterminator {

    private val specialHolidaysRule : List<HolidayRule> = listOf(
        FixedDateHolidayRule("XMas", Month.DECEMBER, 25),
        FixedDateHolidayRule("NewYear", Month.JANUARY, 1),
        FixedDateHolidayRule("BoxingDay", Month.DECEMBER, 26),
        MovableDateHolidayRule("AustraliaDay", 1, 26),
        OcurrenceDateHolidayRule("QueensBirth", 2, DayOfWeek.MONDAY, Month.JUNE),
        OcurrenceDateHolidayRule("LabourDay", 1, DayOfWeek.MONDAY, Month.OCTOBER)
        // catered to the given assignment
        // seems easter is quite more complex: https://www.assa.org.au/edm
        // banking holiday omitted because is not a general holiday
    )

    override fun isHoliday(date: LocalDate): Boolean {
        return date.dayOfWeek == DayOfWeek.SATURDAY ||
                date.dayOfWeek == DayOfWeek.SUNDAY ||
                this.isSpecialHoliday(date)
    }

    private fun isSpecialHoliday(date: LocalDate): Boolean {
        this.specialHolidaysRule.forEach {
            if (it.isHoliday(date))
                return true
        }
        return false
    }

}
