package com.rodvar.nswbusinesdays.business

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.io.File
import java.io.InputStream
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
class NSWHolidays {

//    private val calendarAPI: CalendarAPI = Calendar()

    private suspend fun calculateBusinessDays(dateFrom: LocalDate, dateTo: LocalDate): Int {
        var days = -1
        launchAsync {
            days = 0
        }.await()
        return days
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
        var days = -1
        runBlocking {
            days = calculateBusinessDays(dateFrom = dateFrom, dateTo = dateTo)
            callback(days)
        }
        return days
    }

    private fun <T> launchAsync(function: () -> T): Deferred<T> {
        return GlobalScope.async { function() }
    }


    companion object {

        @JvmStatic
        private fun help(): String {
            return "Welcome to NSW Holidays API\n\n" +
                    "To run a single query pass in 2 dates in yyyy-MM-dd format. e.g. java -jar build/libs/nswholidays.jar 2019-10-01 2019-11-28\n" +
                    "To run a file execute this program like this:\n" +
                    "  $ java -jar build/lib/nswholidays.jar -f test.txt\n\n" +
                    "Where text.txt is your test file\n" +
                    "A test file is already provided, and a script that builds and runs it as well. Simple go:\n" +
                    " $./run_test.sh"
        }

        @JvmStatic
        fun main(args: Array<String>) {
            when {
                args.isEmpty() -> println(help())
                args[0] == "-f" -> {
                    try {
                        val inputStream: InputStream = File(args[1]).inputStream()
                        val inputString = inputStream.bufferedReader().use { it.readText() }.split("\n")
                        inputString.forEach { line ->
                            if (line.isNotEmpty()) {
                                line.split(" ").let {
                                    if (it.isNotEmpty())
                                        println(NSWHolidays().businessDays(it[0], it[1]) { days ->
                                            println(days)
                                        })
                                }
                            }
                        }
                    } catch (e: Exception) {
                        println("Failed to parse file: " + e.localizedMessage)
                    }
                }
                else -> {
                    try {
                        println(NSWHolidays().businessDays(args[0], args[1]) { days ->
                            println(days)
                        })
                    } catch (e: Exception) {
                        println("Couldn't calculate business days in between ${args[0]} and ${args[1]}: ${e.localizedMessage}")
                    }
                }
            }
        }
    }
}
