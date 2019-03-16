package be.kristofbuts.android.customeroverview.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.kristofbuts.android.customeroverview.R
import be.kristofbuts.android.customeroverview.model.Customer
import kotlinx.android.synthetic.main.customer_list_item.view.*

/**
 * This custom adapter will accommodate the list of customers. It contains a custom view holder and specifies an
 * interface for classes that use it so they can react to a user selecting a record.
 */
class CustomerAdapter (
    private val ctx: Context, // required to access string resources etc
    private val customerSelectionListener: CustomerSelectionListener // is triggered when a user selects a record
) : RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder>() {

    // optimisation: get components ready on construction
    class CustomerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivCustomerPhoto: ImageView = view.ivCustomerPhoto
        val tvCustomerName: TextView = view.tvCustomerName
        val tvCustomerCalls: TextView = view.tvCustomerCalls
        val tvCustomerEmail: TextView = view.tvCustomerEmail
    }

    /**
     * This field contains the customer data (which is obtained through a REST call). Upon change, the list should
     * be updated so we have a custom setter.
     */
    var customers: Array<Customer> = arrayOf()
        set(value) {
            field = value
            notifyDataSetChanged() // triggers UI update
        }

    // create new list item by inflating it from the layout file
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val customerView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.customer_list_item, parent, false)

        return CustomerViewHolder(customerView)
    }

    override fun getItemCount(): Int = customers.size // nice Kotlin shorthand

    /**
     * This method is called when a list item is reassigned to a new object. Here we set the UI elements to match
     * the content of the new object.
     */
    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val customer = customers[position] // makes for easier referencing below

        // no need to find the components again, they're already in the viewholder since its construction
        holder.ivCustomerPhoto.setImageBitmap(customer.imageBitmap)
        holder.tvCustomerName.text = customer.getName()
        holder.tvCustomerEmail.text = customer.email
        holder.tvCustomerCalls.text = String.format("%d %s",
            customer.callsToServiceLine, ctx.getString(R.string.no_of_calls))

        // the passed in listener will be called when a user selects a record
        holder.itemView.setOnClickListener {
            customerSelectionListener.onCustomerSelected(position)
        }
    }

    // specify interface for listening to selection events
    interface CustomerSelectionListener {
        fun onCustomerSelected(pos: Int)
    }
}