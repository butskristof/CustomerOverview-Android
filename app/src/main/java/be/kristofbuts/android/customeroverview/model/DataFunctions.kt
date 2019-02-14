package be.kristofbuts.android.customeroverview.model

import be.kristofbuts.android.customeroverview.R
import java.util.*

fun getCustomers(): Array<Customer> {
    val c1 = Customer(0, "Michael", "Lucas", "Geekus", "michael.lucas@geekus.biz", 9, Date(115,4,15), true, R.drawable.michael)
    val c2 = Customer(1, "Briggs", "Puckett", "Bicol", "briggs.puckett@bicol.co.uk", 18, Date(115,11,9), true, R.drawable.briggs)

    return arrayOf(c1, c2)
}