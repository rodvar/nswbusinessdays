package com.rodvar.nswbusinesdays.ui.main

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.rodvar.nswbusinesdays.DateUtils
import com.rodvar.nswbusinesdays.NSWHolidayDeterminator
import com.rodvar.nswbusinesdays.R
import com.rodvar.nswbusinesdays.business.NSWCalendarAPI
import kotlinx.android.synthetic.main.main_fragment.*
import java.util.*


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val service : NSWCalendarAPI = NSWCalendarAPI(NSWHolidayDeterminator()) // should be injected to make this fragment testable

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.dateFrom.setOnClickListener {
            this.showDatePicker(this.dateFromValue)
        }
        this.dateTo.setOnClickListener {
            this.showDatePicker(this.dateToValue)
        }
        this.button.setOnClickListener {
            this.service.businessDays(this.dateFromValue.text.toString(), this.dateToValue.text.toString()) {
                this.result.text = it.toString()
            }
        }
    }

    private fun showDatePicker(v: TextView) {
        val newFragment: DialogFragment = MyDatePickerFragment { date ->
            v.text = date
        }
        newFragment.show(this.fragmentManager!!, "date picker")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

    class MyDatePickerFragment(callback: (String) -> Unit) : DialogFragment() {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val c: Calendar = Calendar.getInstance()
            val year: Int = c.get(Calendar.YEAR)
            val month: Int = c.get(Calendar.MONTH)
            val day: Int = c.get(Calendar.DAY_OF_MONTH)
            return DatePickerDialog(activity!!, dateSetListener, year, month, day)
        }

        private val dateSetListener =
            OnDateSetListener { view, year, month, day ->
                callback("" + view.year +
                        "-" + DateUtils.toDateString(view.month + 1) +
                        "-" + DateUtils.toDateString(view.dayOfMonth))
            }
    }

}
