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

class CustomerAdapter(
    private val ctx: Context, // for getting drawables etc
    private val customerSelectionListener: CustomerSelectionListener // containing activity reacts to selection
) : RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder>() {
    /* Werk een specifieke Adapter klasse uit voor je RecyclerView, plaats deze in een apart package "adapters".
    Werk de nodige methodes uit in de Adapter klasse.
    De ViewHolder maak je aan als inner class van de Adapter klasse.
    Zorg voor een goede optimalisatie: de ViewHolder haalt bij creatie de verschillende referenties naar de
    componenten op via findViewById! */

    // optimisation: get components ready on construction
    class CustomerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivCustomerPhoto: ImageView = view.ivCustomerPhoto
        val tvCustomerName: TextView = view.tvCustomerName
        val tvCustomerCalls: TextView = view.tvCustomerCalls
        val tvCustomerEmail: TextView = view.tvCustomerEmail
    }

    var customers: Array<Customer> = arrayOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    // create new list item by inflating it from the layout file
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val customerView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.customer_list_item, parent, false)

        return CustomerViewHolder(customerView)
    }

    override fun getItemCount(): Int = customers.size

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        // get customer out of top level fn for easy reference below
        val customer = customers[position]

        // no need to find the components again, they're already in the viewholder since its construction
//        holder.ivCustomerPhoto.setImageDrawable(ctx.getDrawable(customer.image)) // get drawables from context
        holder.ivCustomerPhoto.setImageBitmap(customer.imageBitmap)
        holder.tvCustomerName.text = customer.getName()
        holder.tvCustomerEmail.text = customer.email
        holder.tvCustomerCalls.text = String.format("%d calls to service line", customer.callsToServiceLine)

        // couple the trigger of clicking on an item to the passed in listener (should be the containing activity)
        holder.itemView.setOnClickListener {
            customerSelectionListener.onCustomerSelected(position)
        }
    }

    // specify interface for listening to selection events
    interface CustomerSelectionListener {
        fun onCustomerSelected(pos: Int)
    }
}