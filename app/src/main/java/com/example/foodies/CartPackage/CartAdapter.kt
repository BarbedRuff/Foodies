package com.example.foodies.CartPackage

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodies.ProductsList
import com.example.foodies.R
import kotlinx.android.synthetic.main.cartitem.view.*

class CartAdapter: RecyclerView.Adapter<CartAdapter.ProductsViewHolder>() {
    var list = mutableListOf<CartItem>()
    var price = 0
    lateinit var context: View
    class ProductsViewHolder (view: View):RecyclerView.ViewHolder(view)

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cartitem, parent, false)
        return CartAdapter.ProductsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.itemView.Name.text = list[position].Name
        holder.itemView.count.text = list[position].Count.toString()
        holder.itemView.single_price.text = "${list[position].Current_Price.toString()} \u20bd"
        holder.itemView.currentPrice.text = "${list[position].Current_Price.toString()} \u20bd"
        holder.itemView.old_price.text = "${list[position].Old_Price.toString()} \u20bd"
        if(list[position].Old_Price != null){
            holder.itemView.single_price.visibility = View.INVISIBLE
        }
        else{
            holder.itemView.currentPrice.visibility = View.INVISIBLE
            holder.itemView.old_price.visibility = View.INVISIBLE
        }

        /*BindButton*/
        holder.itemView.minus.setOnClickListener{
            price -= list[position].Current_Price
            context.findViewById<TextView>(R.id.centerText).text = "Заказать за ${price} \u20bd"
            if(list[position].Count - 1 == 0){
                removeItem(position)
                if(list.isEmpty()){
                    context.findViewById<RecyclerView>(R.id.products).visibility = View.INVISIBLE
                    context.findViewById<RelativeLayout>(R.id.accept).visibility = View.INVISIBLE
                    context.findViewById<TextView>(R.id.empty).visibility = View.VISIBLE
                }
            }
            else{
                list[position] = CartItem(list[position].Id, list[position].Name, list[position].Count - 1, list[position].Current_Price, list[position].Old_Price)
                holder.itemView.count.text = list[position].Count.toString()
            }
        }
        holder.itemView.plus.setOnClickListener{
            price += list[position].Current_Price
            context.findViewById<TextView>(R.id.centerText).text = "Заказать за ${price} \u20bd"
            list[position] = CartItem(list[position].Id, list[position].Name, list[position].Count + 1, list[position].Current_Price, list[position].Old_Price)
            holder.itemView.count.text = list[position].Count.toString()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeItem(position: Int) {
        list.removeAt(position)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(products: List<CartItem>, ctx: View, prc: Int){
        list = products.toMutableList()
        context = ctx
        price = prc
        notifyDataSetChanged()
    }
}