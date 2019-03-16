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
import be.kristofbuts.android.customeroverview.rest.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * This activity is misleadingly called main, but actually the second activity the user will use. In earlier versions,
 * it was the opening screen.
 * It will show all of a user's data (in a fragment), and have buttons to navigate through the list.
 */
class MainActivity : AppCompatActivity() {
    // UI elements
    private lateinit var btnPrev: Button
    private lateinit var btnNext: Button
    private lateinit var customerDetailFragment: CustomerDetailFragment

    // keep a record of where we are and the number of customers available on the web server
    // the actual data is only needed in the fragment, so we don't keep the whole array here
    private var index: Int = 0
    private var numberOfCustomers: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.initialiseViews()
        this.addEventHandlers()

        this.loadData() // will call api to get number of customers
    }

    private fun initialiseViews() {
        // just UI elements
        this.btnPrev = findViewById(R.id.btnPrev)
        this.btnNext = findViewById(R.id.btnNext)
        this.customerDetailFragment = supportFragmentManager
            .findFragmentById(R.id.customerDetailFragment) as CustomerDetailFragment
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

    private fun loadData() {
        RestClient(this)
            .getCustomers()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                numberOfCustomers = it.size // we only need the number of customers in this activity

                // when coming from overview, customer id is passed in, otherwise start at first element
                val customerIndex = intent.getIntExtra(getString(R.string.CUSTOMER_INDEX), 0)
                this.setCounter(customerIndex) // this will trigger a UI update
            }, {
                Toast.makeText(
                    this@MainActivity,
                    it.message,
                    Toast.LENGTH_LONG)
                    .show()
            })
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

    private fun increaseCounter() {
        // overflow back to zero
        this.index = (this.index + 1) % numberOfCustomers

        this.triggerFragmentUpdate()
    }

    private fun decreaseCounter() {
        // underflow back to array size
        this.index = (this.index + numberOfCustomers - 1) % numberOfCustomers

        this.triggerFragmentUpdate()
    }

    private fun setCounter(pos: Int) {
        this.index = pos

        this.triggerFragmentUpdate()
    }

    private fun triggerFragmentUpdate() {
        this.customerDetailFragment.setCustomerIndex(this.index) // setting the index will make the fragment update
    }

}
