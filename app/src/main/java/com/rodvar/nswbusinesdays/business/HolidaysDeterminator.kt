package com.rodvar.nswbusinesdays.business

import java.time.LocalDate

/**
 * Strategy pattern to encapsulate holiday determination algorithm
 */
interface HolidaysDeterminator {

    /**
     * @return true if the given date is a holiday (sat, sunday or special holiday date)
     */
    fun isHoliday(date: LocalDate): Boolean

}
