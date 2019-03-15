package be.kristofbuts.android.customeroverview.model

import android.graphics.Bitmap
import android.os.Parcelable
import java.io.Serializable
import java.util.*

data class Customer (
    val id: Int,
    val firstName: String,
    val lastName: String,
    val company: String,
    val email: String,
    val callsToServiceLine: Int,
    val registrationDate: GregorianCalendar,
    val isActive: Boolean,
    val image: String,
    var imageBitmap: Bitmap
) {
    fun getName(): String {
        return String.format("%s %s", this.firstName, this.lastName)
    }
}