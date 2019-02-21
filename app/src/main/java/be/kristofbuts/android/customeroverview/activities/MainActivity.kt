package be.kristofbuts.android.customeroverview.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import be.kristofbuts.android.customeroverview.R
import be.kristofbuts.android.customeroverview.fragments.CustomerDetailFragment
import be.kristofbuts.android.customeroverview.model.Customer
import be.kristofbuts.android.customeroverview.model.getCustomers
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    private lateinit var btnPrev: Button
    private lateinit var btnNext: Button
    private lateinit var customerDetailFragment: CustomerDetailFragment
    private var index: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//
        this.initialiseViews()
        this.addEventHandlers()

        val customerIndex = intent.getIntExtra(CUSTOMER_INDEX, 0)
        this.setCounter(customerIndex)
    }

    private fun initialiseViews() {
        this.btnPrev = findViewById(R.id.btnPrev)
        this.btnNext = findViewById(R.id.btnNext)
        this.customerDetailFragment = supportFragmentManager.findFragmentById(R.id.customerDetailFragment) as CustomerDetailFragment
        this.customerDetailFragment.setCustomerIndex(this.index)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null && item.itemId == R.id.about) {
            // start about activity
            val intent = Intent(applicationContext, AboutActivity::class.java)
            startActivity(intent)
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
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
        this.index = (this.index + 1) % getCustomers().size
        this.customerDetailFragment.setCustomerIndex(this.index)
    }

    private fun decreaseCounter() {
        this.index = (this.index + getCustomers().size - 1) % getCustomers().size
        this.customerDetailFragment.setCustomerIndex(this.index)
    }

    private fun setCounter(pos: Int) {
        this.index = pos
        this.customerDetailFragment.setCustomerIndex(this.index)
    }
}
