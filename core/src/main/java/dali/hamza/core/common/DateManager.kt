package dali.hamza.core.common

import java.text.SimpleDateFormat
import java.util.*

class DateManager {

    companion object {
        val qlFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'")
        val appFormat = SimpleDateFormat("dd MMMM yyyy")

        fun setDate(year: Int, month: Int, day: Int): Date {

            val calendar = Calendar.getInstance()

            calendar[Calendar.DAY_OF_MONTH] = day

            calendar[Calendar.MONTH] = month

            calendar[Calendar.YEAR] = year
            return calendar.time
        }

        fun formatToTime(
            dateToFormat: String,
            formatSource: SimpleDateFormat? = appFormat
        ): String {
            val format = formatSource ?: qlFormat
            return appFormat.format(format.parse(dateToFormat))


        }

        fun now(): Date {
            val calendar = Calendar.getInstance()
            return calendar.time
        }
    }
}
