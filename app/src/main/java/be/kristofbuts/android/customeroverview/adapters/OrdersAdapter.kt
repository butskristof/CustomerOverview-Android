package be.kristofbuts.android.customeroverview.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.kristofbuts.android.customeroverview.R
import be.kristofbuts.android.customeroverview.model.Order
import kotlinx.android.synthetic.main.order_list_item.view.*
import java.text.SimpleDateFormat

/**
 * This custom adapter will be used in combination with the RecyclerView in the OrderActivity.
 * There's no reaction to a user selecting an order (yet), so relatively little is necessary to make this work.
 */
class OrdersAdapter(
    private val ctx: Context // required to get string resources
    ) : RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>() {

    // get UI elements on construction of the viewholder
    class OrderViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.tvTitle
        val txtDate: TextView = view.txtDate
        val txtTotal: TextView = view.txtTotal
        val txtCurrency: TextView = view.txtCurrency
    }

    // order data, passed in externally (and asynchronously)
    var orders: Array<Order> = arrayOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val orderView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.order_list_item, parent, false)

        return OrderViewHolder(orderView)
    }

    override fun getItemCount(): Int = orders.size

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position] // easier referencing onwards

        holder.tvTitle.text = String.format("%s %d", ctx.getString(R.string.order), order.id)
        holder.txtDate.text = SimpleDateFormat("yyyy-MM-dd").format(order.date.time) // pretty print
        holder.txtTotal.text = String.format("%.2f", order.total)
        holder.txtCurrency.text = order.currency
    }
}