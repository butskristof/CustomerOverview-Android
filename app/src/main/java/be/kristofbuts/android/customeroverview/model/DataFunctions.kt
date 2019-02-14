package be.kristofbuts.android.customeroverview.model

import be.kristofbuts.android.customeroverview.R
import java.util.*

fun getCustomers(): Array<Customer> {
    val c1 = Customer(0, "Michael", "Lucas", "Geekus", "michael.lucas@geekus.biz", 9, Date(2015,5,15), true, R.drawable.michael)

    return arrayOf(c1)
}