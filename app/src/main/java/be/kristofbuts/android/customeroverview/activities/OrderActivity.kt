package be.kristofbuts.android.customeroverview.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import be.kristofbuts.android.customeroverview.R

class OrderActivity : AppCompatActivity() {

    private var customerIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        customerIndex = intent.getIntExtra(CUSTOMER_INDEX, 0)
    }

    private fun loadData() {
    }
}
