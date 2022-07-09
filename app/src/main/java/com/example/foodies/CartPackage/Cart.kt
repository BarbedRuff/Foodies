package com.example.foodies.CartPackage

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.foodies.ProductsList
import com.example.foodies.ProductsManager
import com.example.foodies.R
import com.example.foodies.databinding.ActivityCartBinding


class Cart : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var productsManager: ProductsManager
    lateinit var cartAdapter: CartAdapter

    companion object{
        const val PRODUCTS = "products";
        const val PRICE = "price";
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    override fun onBackPressed() {
        ProductsList.price = cartAdapter.price
        ProductsList.productsList = toPairInt(cartAdapter.list)
        ProductsList.Update = true
        finish()
    }

    fun init(){
        /*BackListener*/
        val back = findViewById<ImageButton>(R.id.back)
        back.setOnClickListener{
            ProductsList.price = cartAdapter.price
            ProductsList.productsList = toPairInt(cartAdapter.list)
            ProductsList.Update = true
            finish()
        }
        /*Price*/
        val bottom = findViewById<TextView>(R.id.centerText)
        bottom.text = "Заказать за ${ProductsList.price} \u20bd"
        /*RecyclerView*/
        val recView = findViewById<RecyclerView>(R.id.products)
        cartAdapter = CartAdapter()
        cartAdapter.setHasStableIds(true)
        recView.adapter = cartAdapter
        /*Divider*/
        val horizontalDecoration = DividerItemDecoration(
            recView.getContext(),
            DividerItemDecoration.VERTICAL
        )
        val horizontalDivider =
            ContextCompat.getDrawable(this,R.drawable.horizontal_divider)
        horizontalDecoration.setDrawable(horizontalDivider!!)
        recView.addItemDecoration(horizontalDecoration)
        /*SetListAdapter*/
        val productsStream = assets.open("Products.json")
        productsManager = ProductsManager(productsStream)
        cartAdapter.setList(CartProduct(productsManager.products, ProductsList.productsList).list, binding.root, ProductsList.price)
    }

    fun toPairInt(list: MutableList<CartItem>): MutableList<Pair<Int, Int>> {
        var productsList: MutableList<Pair<Int, Int>> = mutableListOf()
        productsList = list.map { Pair(it.Id, it.Count) } as MutableList<Pair<Int, Int>>
        return productsList
    }
}