package be.kristofbuts.android.customeroverview.model

import java.util.*

data class Customer(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val company: String,
    val email: String,
    val callsToServiceLine: Int,
    val registrationDate: GregorianCalendar,
    val isActive: Boolean
//    val image: String
) {
    fun getName(): String {
        return String.format("%s %s", this.firstName, this.lastName)
    }
}