package be.kristofbuts.android.customeroverview.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.kristofbuts.android.customeroverview.R
import be.kristofbuts.android.customeroverview.model.Order
import kotlinx.android.synthetic.main.order_list_item.view.*
import java.text.SimpleDateFormat

class OrdersAdapter(
    private val ctx: Context
    ) : RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>() {

    class OrderViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.tvTitle
        val txtDate: TextView = view.txtDate
        val txtTotal: TextView = view.txtTotal
        val txtCurrency: TextView = view.txtCurrency
    }

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

    override fun getItemCount(): Int {
        return this.orders.size
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]

        holder.tvTitle.text = String.format("%s %d", ctx.getString(R.string.order), order.id)
        holder.txtDate.text = SimpleDateFormat("yyyy-MM-dd").format(order.date.time)
        holder.txtTotal.text = String.format("%.2f", order.total)
        holder.txtCurrency.text = order.currency
    }
}