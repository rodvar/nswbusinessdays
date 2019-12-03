package com.rodvar.nswbusinesdays

import com.rodvar.nswbusinesdays.business.HolidaysDeterminator
import java.time.DayOfWeek
import java.time.LocalDate

class NSWHolidayDeterminator : HolidaysDeterminator {

    override fun isHoliday(date: LocalDate): Boolean {
        return date.dayOfWeek == DayOfWeek.SATURDAY ||
                date.dayOfWeek == DayOfWeek.SUNDAY ||
                this.isSpecialHoliday(date)
    }

    private fun isSpecialHoliday(date: LocalDate): Boolean {
        return false
    }

}
