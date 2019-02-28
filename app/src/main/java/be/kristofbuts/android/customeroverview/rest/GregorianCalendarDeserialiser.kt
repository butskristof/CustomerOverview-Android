package be.kristofbuts.android.customeroverview.rest

import android.util.Log
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class GregorianCalendarDeserialiser: JsonDeserializer<GregorianCalendar> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): GregorianCalendar {

        val date: String = json!!.asString

        val formatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

        try {
            val date: Date = formatter.parse(date)
            val ret: GregorianCalendar = GregorianCalendar()
            ret.time = date

            return ret
        } catch (e: ParseException) {
            Log.i("ParseException", e.message)
            return GregorianCalendar()
        }
    }
}