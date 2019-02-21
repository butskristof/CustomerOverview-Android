package be.kristofbuts.android.customeroverview.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.kristofbuts.android.customeroverview.R
import be.kristofbuts.android.customeroverview.model.getCustomers
import kotlinx.android.synthetic.main.customer_list_item.view.*

class CustomerAdapter(
    private val ctx: Context,
    private val customerSelectionListener: CustomerSelectionListener
) : RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder>() {
    /* Werk een specifieke Adapter klasse uit voor je RecyclerView, plaats deze in een apart package "adapters".
    Werk de nodige methodes uit in de Adapter klasse.
    De ViewHolder maak je aan als inner class van de Adapter klasse.
    Zorg voor een goede optimalisatie: de ViewHolder haalt bij creatie de verschillende referenties naar de
    componenten op via findViewById! */

    class CustomerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivCustomerPhoto: ImageView = view.ivCustomerPhoto
        val tvCustomerName: TextView = view.tvCustomerName
        val tvCustomerCalls: TextView = view.tvCustomerCalls
        val tvCustomerEmail: TextView = view.tvCustomerEmail
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val customerView = LayoutInflater.from(parent.context).inflate(R.layout.customer_list_item, parent, false)
        return CustomerViewHolder(customerView)
    }

    override fun getItemCount(): Int = getCustomers().size

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val customer = getCustomers()[position]
        holder.ivCustomerPhoto.setImageDrawable(ctx.getDrawable(customer.image))
        holder.tvCustomerName.text = String.format("%s %s", customer.firstName, customer.lastName)
        holder.tvCustomerEmail.text = customer.email
        holder.tvCustomerCalls.text = String.format("%d calls to service line", customer.callsToSerivceLine)

        holder.itemView.setOnClickListener {
            customerSelectionListener.onCustomerSelected(position)
        }
    }

    interface CustomerSelectionListener {
        fun onCustomerSelected(pos: Int)
    }
}