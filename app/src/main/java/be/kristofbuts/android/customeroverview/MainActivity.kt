package be.kristofbuts.android.customeroverview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
}
