package com.rodvar.nswbusinesdays

import com.rodvar.nswbusinesdays.business.NSWHolidays
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class NSWHolidaysTest {

    private val service = NSWHolidays()

    @Test
    fun testBasicExample1() {
        // need to mock the service to return calculation sync
        runBlocking {
            service.businessDays("2014-08-07", "2014-08-07") { days ->
                assertEquals(1, days)
            }
        }
    }

    @Test
    fun testBasicExample2() {
        runBlocking {
            service.businessDays("2014-08-13", "2014-08-21") { days ->
                assertEquals(5, days)
            }
        }
    }

}
