package com.example.foodies

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.item.view.*


class ProductsAdapter(): RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    private var list = emptyList<Products>()
    lateinit var cartManager: CartManager
    lateinit var carDatatManager: CartDataManager
    lateinit var categoriesManager: CategoriesManager
    lateinit var ctx: View

    class ProductsViewHolder(view: View):RecyclerView.ViewHolder(view)

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ProductsViewHolder(view)
    }

    fun GetProductByName(name: String): Products? {
        for (i in list){
            if(i.name == name){
                return i
            }
        }
        return null
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.itemView.sale_img.visibility = View.INVISIBLE
        holder.itemView.counter.visibility = View.INVISIBLE
        holder.itemView.buy.visibility = View.INVISIBLE
        holder.itemView.buy.currentPrice.visibility = View.INVISIBLE
        holder.itemView.buy.saleprice.visibility = View.INVISIBLE
        holder.itemView.buy.saleprice.visibility = View.INVISIBLE

        holder.itemView.name_product.text = list[position].name.toString()
        holder.itemView.measure.text = "${list[position].measure.toString()} ${list[position].measure_unit}"
        if (list[position].price_old != null){
            holder.itemView.sale_img.visibility = View.VISIBLE
            holder.itemView.buy.saleprice.visibility = View.VISIBLE
            holder.itemView.SaleCurrentPrice.text = "${list[position].price_current.toString()} \u20bd"
            holder.itemView.SaleOldPrice.text = "${list[position].price_old.toString()} \u20bd"
        }
        else{
            holder.itemView.buy.currentPrice.visibility = View.VISIBLE
            holder.itemView.currentPrice.text = "${list[position].price_current.toString()} \u20bd"
        }
        var prCount = carDatatManager.GetCountByName(list[position].name.toString())
        if(prCount == 0){
            holder.itemView.buy.visibility = View.VISIBLE
            holder.itemView.buy.isClickable = true
            holder.itemView.counter.minus.isClickable = false
            holder.itemView.counter.plus.isClickable = false

        }
        else{
            holder.itemView.counter.visibility = View.VISIBLE
            holder.itemView.counter.count.text = prCount.toString()
            holder.itemView.buy.isClickable = false
            holder.itemView.counter.plus.isClickable = true
            holder.itemView.counter.minus.isClickable = true
        }



        /*ClickListeners*/
        holder.itemView.product_img.setOnClickListener{
            var intent = Intent(it.context,Product::class.java)
            val product = GetProductByName(holder.itemView.name_product.text.toString())
            if (product != null){
                val count = carDatatManager.GetCountByName(product.name)
                intent.putExtra("id", product.id)
                intent.putExtra("pos_rec", position)
                intent.putExtra("position", categoriesManager.getPosById(product.category_id))
                intent.putExtra("name", product.name)
                intent.putExtra("price",  "${product.price_current.toString()} \u20BD")
                intent.putExtra("desc", product.description.toString())
                intent.putExtra("measure", "${product.measure.toString()} ${product.measure_unit.toString()}")
                intent.putExtra("energy", "${product.energy_per_100_grams.toString()} ккал")
                intent.putExtra("proteins", "${product.proteins_per_100_grams.toString()} ${product.measure_unit.toString()}")
                intent.putExtra("fats_per", "${product.fats_per_100_grams.toString()} ${product.measure_unit.toString()}")
                intent.putExtra("carbohydrates_per_100_grams", "${product.carbohydrates_per_100_grams .toString()} ${product.measure_unit.toString()}")
                intent.putExtra("count", count)
                intent.putExtra("cartManager", carDatatManager)
            }
            it.context.startActivity(intent)
        }

        holder.itemView.buy.setOnClickListener{
            cartManager.append(holder.itemView.name_product.text.toString())
            holder.itemView.counter.count.text = "1"
            holder.itemView.buy.visibility = View.INVISIBLE
            holder.itemView.buy.isClickable = false
            holder.itemView.counter.visibility = View.VISIBLE
            holder.itemView.counter.plus.isClickable = true
            holder.itemView.counter.minus.isClickable = true
        }
        holder.itemView.counter.minus.setOnClickListener{
            val count = carDatatManager.GetCountByName(holder.itemView.name_product.text.toString())
            holder.itemView.counter.count.text = (count - 1).toString()
            cartManager.pop(holder.itemView.name_product.text.toString())
            if(count == 1){
                holder.itemView.buy.visibility = View.VISIBLE
                holder.itemView.buy.isClickable = true
                holder.itemView.counter.visibility = View.INVISIBLE
                holder.itemView.counter.plus.isClickable = false
                holder.itemView.counter.minus.isClickable = false
            }
        }
        holder.itemView.counter.plus.setOnClickListener{
            val count = carDatatManager.GetCountByName(holder.itemView.name_product.text.toString()) + 1
            cartManager.append(holder.itemView.name_product.text.toString())
            holder.itemView.counter.count.text = count.toString()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(products: List<Products>, cM: CartManager, cdm: CartDataManager, catgManage: CategoriesManager, context: View){
        list = products
        cartManager = cM
        carDatatManager = cdm
        categoriesManager = catgManage
        ctx = context
        notifyDataSetChanged()
    }

}