package be.kristofbuts.android.customeroverview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import be.kristofbuts.android.customeroverview.model.Customer
import be.kristofbuts.android.customeroverview.model.getCustomers
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {

    private lateinit var txtID: TextView
    private lateinit var txtName: TextView
    private lateinit var txtCompany: TextView
    private lateinit var txtEmail: TextView
    private lateinit var txtCalls: TextView
    private lateinit var txtRegistration: TextView
    private lateinit var txtActive: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.initialiseViews()
        this.addEventHandlers()
    }

    private fun initialiseViews() {
        this.txtID = findViewById(R.id.txtId)
        this.txtName = findViewById(R.id.txtName)
        this.txtCompany = findViewById(R.id.txtCompany)
        this.txtEmail = findViewById(R.id.txtEmail)
        this.txtCalls = findViewById(R.id.txtCalls)
        this.txtRegistration = findViewById(R.id.txtRegistration)
        this.txtActive = findViewById(R.id.txtActive)

        this.fillCustomerInfo(getCustomers()[0])
    }

    private fun addEventHandlers() {
        // add btn events
    }

    private fun fillCustomerInfo(c: Customer) {
        this.txtID.text = c.id.toString()
        this.txtName.text = String.format("%s %s", c.firstName, c.lastName)
        this.txtCompany.text = c.company
        this.txtEmail.text = c.email
        this.txtCalls.text = c.callsToSerivceLine.toString()
        this.txtRegistration.text = SimpleDateFormat("yyyy-MM-dd").format(c.registrationDate)
        this.txtActive.text = if (c.isActive) getString(R.string.yes) else getString(R.string.no)
    }
}
