package be.kristofbuts.android.customeroverview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import be.kristofbuts.android.customeroverview.model.Customer
import be.kristofbuts.android.customeroverview.model.getCustomers
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    private lateinit var btnPrev: Button
    private lateinit var btnNext: Button
    private lateinit var imgCustomer: ImageView
    private lateinit var txtID: TextView
    private lateinit var txtName: TextView
    private lateinit var txtCompany: TextView
    private lateinit var txtEmail: TextView
    private lateinit var txtCalls: TextView
    private lateinit var txtRegistration: TextView
    private lateinit var txtActive: TextView

    private lateinit var customers: ArrayList<Customer>
    private var counter: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.customers = getCustomers()

        this.initialiseViews()
        this.addEventHandlers()
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

    private fun initialiseViews() {
        this.btnPrev = findViewById(R.id.btnPrev)
        this.btnNext = findViewById(R.id.btnNext)

        this.imgCustomer = findViewById(R.id.imgCustomer)
        this.txtID = findViewById(R.id.txtId)
        this.txtName = findViewById(R.id.txtName)
        this.txtCompany = findViewById(R.id.txtCompany)
        this.txtEmail = findViewById(R.id.txtEmail)
        this.txtCalls = findViewById(R.id.txtCalls)
        this.txtRegistration = findViewById(R.id.txtRegistration)
        this.txtActive = findViewById(R.id.txtActive)

        this.fillCustomerInfo(this.customers[counter])
    }

    private fun addEventHandlers() {
        // add btn events
        this.btnPrev.setOnClickListener {
            this.decreaseCounter()
            this.fillCustomerInfo(this.customers[counter])
        }

        this.btnNext.setOnClickListener {
            this.increaseCounter()
            this.fillCustomerInfo(this.customers[counter])
        }

        this.imgCustomer.setOnClickListener {
            // start new activity
            val intent = Intent(applicationContext, ImageActivity::class.java).apply {
                putExtra("image", customers[counter].image)
            }
            startActivity(intent)
        }
    }

    private fun fillCustomerInfo(c: Customer) {
        this.imgCustomer.setImageDrawable(getDrawable(c.image))
        this.txtID.text = c.id.toString()
        this.txtName.text = String.format("%s %s", c.firstName, c.lastName)
        this.txtCompany.text = c.company
        this.txtEmail.text = c.email
        this.txtCalls.text = c.callsToSerivceLine.toString()
        this.txtRegistration.text = SimpleDateFormat("yyyy-MM-dd").format(c.registrationDate)
        this.txtActive.text = if (c.isActive) getString(R.string.yes) else getString(R.string.no)
    }

    private fun increaseCounter() {
        this.counter = (this.counter + 1) % this.customers.size
    }

    private fun decreaseCounter() {
        this.counter = (this.counter + this.customers.size - 1) % this.customers.size
    }

    private fun startImgDetail() {

    }
}
