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

/**
 * Here we will show all of a specific customer's orders. We get the ID passed in and make a specific API call that
 * returns only the orders of the current customer.
 * We show a few details of each order in a RecyclerView.
 */
class OrderActivity : AppCompatActivity() {

    // UI elements
    private lateinit var lblName: TextView // title
    private lateinit var rvOrders: RecyclerView

    // information about the customer. We only need his ID (to get the orders) and his name to
    // use as the title (which was a request)
    private var customerIndex: Int = 0
    private var customerName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        // We should get some data passed in through the intent, otherwise we show the
        // first customer's orders
        this.customerIndex = intent.getIntExtra(getString(R.string.CUSTOMER_INDEX), 0)
        this.customerName = intent.getStringExtra(getString(R.string.CUSTOMER_NAME))

        this.initialiseViews()
        this.addEventHandlers()

        this.loadOrders()
    }

    private fun initialiseViews() {
        this.lblName = findViewById(R.id.tvCustomerName)
        this.lblName.text = this.customerName // set title to customer's name

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
            .getOrdersForCustomer(this.customerIndex) // only get orders for this customer
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                (rvOrders.adapter as OrdersAdapter).orders = it // pass on data to RecyclerView
            }, {
                Toast.makeText(
                    this@OrderActivity,
                    it.message,
                    Toast.LENGTH_LONG
                ).show()
            })
    }

}
