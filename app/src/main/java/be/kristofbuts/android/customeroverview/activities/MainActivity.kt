package be.kristofbuts.android.customeroverview.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import be.kristofbuts.android.customeroverview.R
import be.kristofbuts.android.customeroverview.fragments.CustomerDetailFragment
import be.kristofbuts.android.customeroverview.model.Customer
import be.kristofbuts.android.customeroverview.rest.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    private lateinit var btnPrev: Button
    private lateinit var btnNext: Button
    private lateinit var customerDetailFragment: CustomerDetailFragment

    private var index: Int = 0
    var customers: Array<Customer> = arrayOf()
        set(value) {
            field = value
            customerDetailFragment.customers = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.initialiseViews()
        this.addEventHandlers()

        this.loadData()
    }

    private fun loadData() {
        RestClient(this)
            .getCustomers()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                customers = it

                // when coming from overview, customer id is passed in, otherwise start at first element
                val customerIndex = intent.getIntExtra(CUSTOMER_INDEX, 0)
                this.setCounter(customerIndex)
            }, {
                Toast.makeText(
                    this@MainActivity,
                    it.message,
                    Toast.LENGTH_LONG)
                    .show()
            })
    }

    private fun initialiseViews() {
        this.btnPrev = findViewById(R.id.btnPrev)
        this.btnNext = findViewById(R.id.btnNext)
        this.customerDetailFragment = supportFragmentManager
            .findFragmentById(R.id.customerDetailFragment) as CustomerDetailFragment
    }

    // add menu in upper left
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val result: Boolean

        if (item != null && item.itemId == R.id.about) {
            // start about activity
            val intent = Intent(applicationContext, AboutActivity::class.java)
            startActivity(intent)

            result = true
        } else {
            result = super.onOptionsItemSelected(item)
        }

        return result
    }

    private fun addEventHandlers() {
        // add btn events
        this.btnPrev.setOnClickListener {
            this.decreaseCounter()
        }

        this.btnNext.setOnClickListener {
            this.increaseCounter()
        }

    }

    private fun increaseCounter() {
        // overflow back to zero
        this.index = (this.index + 1) % customers.size

        this.triggerFragmentUpdate()
    }

    private fun decreaseCounter() {
        // underflow back to array size
        this.index = (this.index + customers.size - 1) % customers.size

        this.triggerFragmentUpdate()
    }

    private fun setCounter(pos: Int) {
        // TODO add check?
        this.index = pos

        this.triggerFragmentUpdate()
    }

    private fun triggerFragmentUpdate() {
        this.customerDetailFragment.setCustomerIndex(this.index)
    }

}
