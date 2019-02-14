package be.kristofbuts.android.customeroverview.model

import java.util.*

class Order(
    val id: Int,
    val date: Date,
    val total: Double,
    val currency: String,
    val customerId: Int
)