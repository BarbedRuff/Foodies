package com.example.foodies

import ZoomOutPageTransformer
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.foodies.CartPackage.Cart
import com.example.foodies.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment.view.*


class MainActivity : AppCompatActivity() {
    private lateinit var categoriesManager: CategoriesManager
    private lateinit var productsManager: ProductsManager
    private lateinit var cartManager: CartManager
    private lateinit var products: ViewPager2
    private lateinit var cartDataManager: CartDataManager
    private lateinit var binding: ActivityMainBinding
    private lateinit var meow: ViewPagerAdapter
    private lateinit var categories: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    override fun onResume() {
        if(CountProduct.update){
            cartDataManager.UpdateData(CountProduct.name, CountProduct.count)
            cartManager.Update()
            products.adapter = ViewPagerAdapter(this, productsManager, categoriesManager, cartManager, cartDataManager, binding.root)
            products.setCurrentItem(CountProduct.categoriesPos, false)
            CountProduct.update = false
        }
        if(ProductsList.Update){
            cartDataManager.setList(ProductsList.productsList)
            cartManager.Update()
            products.adapter = ViewPagerAdapter(this, productsManager, categoriesManager, cartManager, cartDataManager, binding.root)
            products.setCurrentItem(ProductsList.categoriesPos, false)
            ProductsList.Update = false
        }
        super.onResume()
    }

    @SuppressLint("ClickableViewAccessibility", "ResourceAsColor")
    private fun init(){
        /*Layers*/
        val mainLayer = findViewById<RelativeLayout>(R.id.main_layer)
        val filterLayer = findViewById<RelativeLayout>(R.id.filter_layer)
        mainLayer.bringToFront()
        val cartButton = findViewById<RelativeLayout>(R.id.toCart)
        /*Categories*/
        categories = findViewById<TabLayout>(R.id.categories)
        val categoriesStream = assets.open("Categories.json")
        categoriesManager = CategoriesManager(categoriesStream)
        /*Products*/
        products = findViewById<ViewPager2>(R.id.products)
        products.setPageTransformer(ZoomOutPageTransformer())
        val productsStream = assets.open("Products.json")
        productsManager = ProductsManager(productsStream)
        /*Cart*/
        val cartlayout = findViewById<RelativeLayout>(R.id.bottom)
        val cart = cartlayout.findViewById<TextView>(R.id.centerText)
        val prLayout = findViewById<RelativeLayout>(R.id.bottom)
        cartDataManager = CartDataManager(productsManager)
        cartManager = CartManager(this, cart, prLayout, products, categories, cartDataManager, cartButton)
        /*ProductsAdapter*/
        products.adapter = ViewPagerAdapter(this, productsManager, categoriesManager, cartManager, cartDataManager, binding.root)
        /*CategoriesMediator*/
        TabLayoutMediator(
            categories, products,false, false
        ) { tab, position -> tab.text = categoriesManager.categories[position].name}.attach()
        /*CartButtonBinding*/
        cartButton.setOnClickListener{
            ProductsList.productsList = cartDataManager.productsList
            ProductsList.price = cartDataManager.price
            ProductsList.categoriesPos = categories.selectedTabPosition
            ProductsList.scrollPos = ((products.recProducts.layoutManager as GridLayoutManager).findLastVisibleItemPosition() + (products.recProducts.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()) / 2
            val intent = Intent(this, Cart::class.java)
            startActivity(intent)
        }
        cartButton.isClickable = false
        /*SearchButton*/
        val button_search = findViewById<ImageButton>(R.id.button_search)
        button_search.setOnClickListener{
            val intent = Intent(this, SearchActivity::class.java)
            ProductsList.productsList = cartDataManager.productsList
            ProductsList.price = cartDataManager.price
            ProductsList.categoriesPos = categories.selectedTabPosition
            ProductsList.scrollPos = ((products.recProducts.layoutManager as GridLayoutManager).findLastVisibleItemPosition() + (products.recProducts.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()) / 2
            startActivity(intent)
        }
        /*FilterButton*/
        val button_filter = findViewById<ImageButton>(R.id.button_filter)
        button_filter.setOnClickListener{
            mainLayer.isClickable = false
            filterLayer.bringToFront()
            this.window.statusBarColor = R.color.shadow
        }
    }
}