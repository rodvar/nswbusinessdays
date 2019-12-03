package com.rodvar.nswbusinesdays.business

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.lang.IllegalArgumentException
import java.time.LocalDate


/**
 * Created by rodvar on 02/12/2019
 *
 * See README.md file for details
 *
 * Please ensure:
 *
 *  - The solution has scalable code and takes into account flexibility for public holidays.
 *  - That we are able to change the calculation algorithm, so that we can mock services and test it.
 *  - There is use of local async service which provides holidays.
 *  - Consideration is given to the question regarding performance issues.
 *  - We expect a local service to be implemented which returns result (holidays) in asynchronous way
 *  - The service should mimic DB or API call and just return locally stored holidays
 *  - It is about general application architecture, structure, components, dependencies, etc
 */
class NSWCalendarAPI(private val holidaysDeterminator : HolidaysDeterminator) {

//    private val calendarAPI: CalendarAPI = Calendar()

    /**
     * @return the amount of weekdays days in between the given dates
     */
    private suspend fun calculateBusinessDays(dateFrom: LocalDate, dateTo: LocalDate): Int {
        var businessDays = 0
        launchAsync {
            var date = dateFrom.plusDays(1) //skip first
            while (date.isBefore(dateTo)) {
                if (!this.holidaysDeterminator.isHoliday(date))
                    businessDays++
                date = date.plusDays(1)
            }
        }.await()
        return businessDays
    }

    /**
     * returns the amount of business days between 2 dates (excluding those)
     *
     * @param dateFromText date in the format yyyy-MM-dd
     * @param dateToText date in the format yyyy-MM-dd
     * @return amount of business days in between the given dates
     */
    fun businessDays(dateFromText: String, dateToText: String, callback: (days: Int) -> Unit): Int {
        val dateFrom = LocalDate.parse(dateFromText)
        val dateTo = LocalDate.parse(dateToText)
        if (dateFrom.isAfter(dateTo))
            throw IllegalArgumentException("Illegal dates $dateFromText to $dateToText")
        var days = 0
        runBlocking {
            days = calculateBusinessDays(dateFrom = dateFrom, dateTo = dateTo)
            callback(days)
        }
        return days
    }

    private fun <T> launchAsync(function: () -> T): Deferred<T> {
        return GlobalScope.async { function() }
    }
}
