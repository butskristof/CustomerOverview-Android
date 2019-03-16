package be.kristofbuts.android.customeroverview.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable

import be.kristofbuts.android.customeroverview.R
import be.kristofbuts.android.customeroverview.activities.*
import be.kristofbuts.android.customeroverview.model.Customer
import be.kristofbuts.android.customeroverview.model.getCustomers
import be.kristofbuts.android.customeroverview.rest.RestClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_customer_detail.view.*
import java.text.SimpleDateFormat

/**
 * A simple [Fragment] subclass.
 *
 */
class CustomerDetailFragment() : Fragment() {
    // layout properties
    private lateinit var ivCustomer: ImageView
    private lateinit var tvID: TextView
    private lateinit var tvName: TextView
    private lateinit var tvCompany: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvCalls: TextView
    private lateinit var tvRegistration: TextView
    private lateinit var tvActive: TextView

    private lateinit var btnOrders: Button

    private var index: Int = 0
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
        // init layout props
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
            val intent = Intent(context!!.applicationContext, ImageActivity::class.java).apply {
                // pass in img ref
//                putExtra(CUSTOMER_INDEX, index)
                putExtra(CUSTOMER_IMG_URL, customer.image)
            }
            startActivity(intent)
        }

        this.btnOrders.setOnClickListener {
            val intent = Intent(context!!.applicationContext, OrderActivity::class.java).apply {
                putExtra(CUSTOMER_INDEX, index)
                putExtra(CUSTOMER_NAME, customer.getName())
            }
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
        if (this::tvRegistration.isInitialized) tvRegistration.text = SimpleDateFormat("yyyy-MM-dd").format(customer.registrationDate.time)
        if (this::tvActive.isInitialized) tvActive.text = if (customer.isActive) getString(R.string.yes) else getString(R.string.no)
    }

    // update customer to show and trigger update
    fun setCustomerIndex(custIndex: Int) {
        this.index = custIndex
        loadCustomer()
    }

    fun loadCustomer() {
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
                    Toast.LENGTH_LONG)
                    .show()
            })
    }
}
