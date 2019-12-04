package com.rodvar.nswbusinesdays

import com.rodvar.nswbusinesdays.business.NSWCalendarAPI
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class NSWCalendarAPITest {

    private var service : NSWCalendarAPI? = null

    init {
        this.service = NSWCalendarAPI(NSWHolidayDeterminator())
    }

    @Before
    fun setup() {

    }

    @Test
    fun testBasicExample1() {
        // need to mock the service to return calculation sync
        runBlocking {
            service?.businessDays("2014-08-07", "2014-08-11") { days ->
                assertEquals(1, days)
            }
        }
    }

    @Test
    fun testBasicExample2() {
        runBlocking {
            service?.businessDays("2014-08-13", "2014-08-21") { days ->
                assertEquals(5, days)
            }
        }
    }

    @Test
    fun testNewYear() {
        runBlocking {
            service?.businessDays("2019-12-31", "2020-01-02") { days ->
                assertEquals(0, days)
            }
        }
    }

    @Test
    fun testAustraliaDayMoved() {
        runBlocking {
            service?.businessDays("2020-01-26", "2020-01-28") { days ->
                assertEquals(0, days)
            }
        }
    }

    @Test
    fun testQueensBirthday() {
        runBlocking {
            service?.businessDays("2019-06-09", "2019-06-11") { days ->
                assertEquals(0, days)
            }
        }
    }

    @Test
    fun textXmas() {
        runBlocking {
            service?.businessDays("2019-12-19", "2019-12-31") { days ->
                assertEquals(5, days)
            }
        }
    }

}
