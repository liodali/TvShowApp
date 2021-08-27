package dali.hamza.tvshowapp.ui.common

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import dali.hamza.core.common.DateManager
import java.util.*

class DatePickerFragment(private val onPressed: (Date) -> Unit, private val onCancel: onPressed) :
    DialogFragment(),
    DatePickerDialog.OnDateSetListener {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val maxCalendar = Calendar.getInstance()
        maxCalendar.set(Calendar.YEAR, c.get(Calendar.YEAR) + 2)
        maxCalendar.set(Calendar.MONTH, 11)
        maxCalendar.set(Calendar.DAY_OF_MONTH, 31)
        isCancelable = false

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(requireContext(), this, year, month, day).apply {
            datePicker.maxDate = maxCalendar.time.time
        }
    }


    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        onPressed(DateManager.setDate(year, month, dayOfMonth))
    }

}