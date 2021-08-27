package dali.hamza.core.common

import java.text.SimpleDateFormat
import java.util.*

class DateManager {

    companion object {
        val qlFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'")
        val appFormat = SimpleDateFormat("dd MMMM yyyy")


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
