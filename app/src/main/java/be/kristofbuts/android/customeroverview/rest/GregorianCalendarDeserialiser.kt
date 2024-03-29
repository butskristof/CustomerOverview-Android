package be.kristofbuts.android.customeroverview.rest

import android.util.Log
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Utility class to convert the JSON dates to GregorianCalender (which we use in the Customer class).
 */
class GregorianCalendarDeserialiser: JsonDeserializer<GregorianCalendar> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): GregorianCalendar {

        val dateStr: String = json!!.asString

        val formatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        try {
            val date: Date = formatter.parse(dateStr)
            val ret: GregorianCalendar = GregorianCalendar()
            ret.time = date

            return ret
        } catch (e: ParseException) {
            Log.i("ParseException", e.message)
            return GregorianCalendar()
        }
    }
}