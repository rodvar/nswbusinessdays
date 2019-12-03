package com.rodvar.nswbusinesdays

object DateUtils {

    fun toDateString(dateValue: Int): String {
        return if (dateValue < 10)
            "0$dateValue"
        else
            dateValue.toString()
    }
}