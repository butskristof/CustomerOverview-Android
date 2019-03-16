package be.kristofbuts.android.customeroverview.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.kristofbuts.android.customeroverview.R
import be.kristofbuts.android.customeroverview.adapters.OrdersAdapter
import be.kristofbuts.android.customeroverview.rest.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class OrderActivity : AppCompatActivity() {

    private lateinit var lblName: TextView
    private lateinit var rvOrders: RecyclerView

    private var customerIndex: Int = 0
    private var customerName: String = ""
//    private lateinit var customerOrders: Array<Order>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        this.initialiseViews()
        this.addEventHandlers()

        customerIndex = intent.getIntExtra(CUSTOMER_INDEX, 0)
        customerName = intent.getStringExtra(CUSTOMER_NAME)

        this.loadOrders()
    }

    private fun initialiseViews() {
        this.lblName = findViewById(R.id.tvCustomerName)
        this.lblName.text = this.customerName

        this.rvOrders = findViewById(R.id.rvOrders)
        this.rvOrders.apply {
            layoutManager = LinearLayoutManager(this@OrderActivity)
            adapter = OrdersAdapter(this@OrderActivity)
        }
    }

    private fun addEventHandlers() {
        // nothing to do here
    }

    private fun loadOrders() {
        RestClient(this)
            .getOrdersForCustomer(this.customerIndex)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                (rvOrders.adapter as OrdersAdapter).orders = it
            }, {
                Toast.makeText(
                    this@OrderActivity,
                    it.message,
                    Toast.LENGTH_LONG
                ).show()
            })
    }

    private fun loadCustomerName() {
        RestClient(this)
            .getOrdersForCustomer(this.customerIndex)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                (rvOrders.adapter as OrdersAdapter).orders = it
            }, {
                Toast.makeText(
                    this@OrderActivity,
                    it.message,
                    Toast.LENGTH_LONG
                ).show()
            })
    }
}
