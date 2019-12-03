package com.rodvar.nswbusinesdays.domain

import java.time.LocalDate

interface HolidayRule {

    fun isHoliday(date: LocalDate) : Boolean
}