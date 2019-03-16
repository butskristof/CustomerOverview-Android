package be.kristofbuts.android.customeroverview.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import be.kristofbuts.android.customeroverview.R
import be.kristofbuts.android.customeroverview.activities.*
import be.kristofbuts.android.customeroverview.model.Customer
import be.kristofbuts.android.customeroverview.rest.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_customer_detail.view.*
import java.text.SimpleDateFormat

/**
 * Here we will show a detailed view of the customer's data. It will be used in both the MainActivity (portrait) and
 * the OverviewActivity (landscape). It provides a link to the OrderActivity.
 *
 */
class CustomerDetailFragment() : Fragment() {
    // UI elements
    private lateinit var ivCustomer: ImageView
    private lateinit var tvID: TextView
    private lateinit var tvName: TextView
    private lateinit var tvCompany: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvCalls: TextView
    private lateinit var tvRegistration: TextView
    private lateinit var tvActive: TextView

    private lateinit var btnOrders: Button

    /**
     * We keep this private and only provide access through a setter method, so we can enforce collecting new data
     * on change.
     */
    private var index: Int = -1 // default to -1, loadCustomer will only act on >= 0
    /**
     * We only keep a single customer's data, and it's obtained through a REST call with the customer's id
     */
    private lateinit var customer: Customer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment but don't return it immediately
        val view = inflater.inflate(R.layout.fragment_customer_detail, container, false)

        this.initialiseViews(view)
        this.addEventHandlers()

        this.loadCustomer()

        return view
    }

    private fun initialiseViews(view: View) {
        this.ivCustomer = view.imgCustomer
        this.tvID = view.txtId
        this.tvName = view.txtName
        this.tvCompany = view.txtCompany
        this.tvEmail = view.txtEmail
        this.tvCalls = view.txtCalls
        this.tvRegistration = view.txtRegistration
        this.tvActive = view.txtActive
        this.btnOrders = view.btnOrders
    }

    private fun addEventHandlers() {
        // photo should open in separate activity which should be started in the context of the containing activity
        this.ivCustomer.setOnClickListener {
            val intent = Intent(context!!.applicationContext, ImageActivity::class.java)
            intent.putExtra(getString(R.string.CUSTOMER_IMG_URL), customer.image)
            startActivity(intent)
        }

        // pass in the customer's index (so it can collect the orders) and name (so it can set the title)
        this.btnOrders.setOnClickListener {
            val intent = Intent(context!!.applicationContext, OrderActivity::class.java)
            intent.putExtra(getString(R.string.CUSTOMER_INDEX), index)
            intent.putExtra(getString(R.string.CUSTOMER_NAME), customer.getName())
            startActivity(intent)
        }
    }

    private fun updateFields() {
        // check whether properties are initialised first!!

        if (this::ivCustomer.isInitialized) ivCustomer.setImageBitmap(customer.imageBitmap)
        if (this::tvID.isInitialized) tvID.text = customer.id.toString()
        if (this::tvName.isInitialized) tvName.text = customer.getName()
        if (this::tvCompany.isInitialized) tvCompany.text = customer.company
        if (this::tvEmail.isInitialized) tvEmail.text = customer.email
        if (this::tvCalls.isInitialized) tvCalls.text = customer.callsToServiceLine.toString()
        if (this::tvRegistration.isInitialized) tvRegistration.text =
            SimpleDateFormat("yyyy-MM-dd").format(customer.registrationDate.time)
        if (this::tvActive.isInitialized) tvActive.text =
            if (customer.isActive) getString(R.string.yes) else getString(R.string.no)
    }

    /**
     * We update the index value and trigger a refresh of the data (and UI)
     */
    fun setCustomerIndex(custIndex: Int) {
        this.index = custIndex
        loadCustomer()
    }

    /**
     * Collect a single customer's data from the web server and update the UI.
     */
    fun loadCustomer() {
        if (index >= 0) { // only load when index is modified from it's default value
            RestClient(context!!.applicationContext)
                .getCustomer(index)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    customer = it
                    updateFields()
                }, {
                    Toast.makeText(
                        context!!.applicationContext,
                        it.message,
                        Toast.LENGTH_LONG
                    )
                        .show()
                })
        }
    }
}
