package com.rodvar.nswbusinesdays

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rodvar.nswbusinesdays.business.NSWCalendarAPI
import com.rodvar.nswbusinesdays.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            // Instead of creating the instance here, an instance provider (like Dagger) is recommended to manage the injection
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance(NSWCalendarAPI(NSWHolidayDeterminator())))
                .commitNow()
        }
    }

}
