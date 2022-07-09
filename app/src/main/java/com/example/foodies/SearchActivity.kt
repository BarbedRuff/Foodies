package com.example.foodies

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.foodies.CartPackage.CartItem
import com.example.foodies.SearchPackage.ProductsAdapter
import com.example.foodies.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    lateinit var textFull: RelativeLayout.LayoutParams
    lateinit var textEmpty: RelativeLayout.LayoutParams
    lateinit var productsAdapter: ProductsAdapter
    lateinit var cartDataManager: CartDataManager
    lateinit var categoriesManager: CategoriesManager
    lateinit var productsManager: ProductsManager
    lateinit var editText: EditText
    var textInput = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init_LayoutParams()
        init()
    }

    override fun onResume() {
        if(CountProduct.update){
            cartDataManager.UpdateData(CountProduct.name, CountProduct.count)
            val list = filter_data(editText.text.toString())
            productsAdapter.setList(list)
            CountProduct.update = false
        }
        super.onResume()
    }

    fun init_LayoutParams(){
        textFull = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        textFull.addRule(RelativeLayout.END_OF, R.id.back)
        textFull.addRule(RelativeLayout.CENTER_VERTICAL, 1)
        textFull.addRule(RelativeLayout.START_OF, R.id.cancel)
        textFull.rightMargin = 0

        textEmpty = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        textEmpty.rightMargin = (this.resources.displayMetrics.density * 20).toInt()
        textEmpty.addRule(RelativeLayout.END_OF, R.id.back)
        textEmpty.addRule(RelativeLayout.CENTER_VERTICAL, 1)
    }

    fun filter_data(text: String): List<Products> {
        textInput = text.replace("\\s+".toRegex()){it.value[0].toString()}.lowercase().trim()
        val list1 = productsManager.products.filter {
            textInput in it.name.lowercase()
        }
        val list2 = productsManager.products.filter{
            textInput in it.description.toString()
        }
        val list3 = list1 + list2
        val list4 = list3.toSet().toList()
        return list4
    }

    override fun onBackPressed() {
        ProductsList.price = cartDataManager.price
        ProductsList.productsList = cartDataManager.productsList
        ProductsList.Update = true
        finish()
    }

    fun init(){
        editText = findViewById<EditText>(R.id.search)
        val alias = findViewById<TextView>(R.id.centerText)
        val cancel = findViewById<ImageButton>(R.id.cancel)
        val recView = findViewById<RecyclerView>(R.id.recProducts)
        val back = findViewById<ImageButton>(R.id.back)

        val productsStream = assets.open("Products.json")
        productsManager = ProductsManager(productsStream)

        val categoriesStream = assets.open("Categories.json")
        categoriesManager = CategoriesManager(categoriesStream)

        cartDataManager = CartDataManager(productsManager)
        cartDataManager.setList(ProductsList.productsList)

        productsAdapter = ProductsAdapter(cartDataManager, categoriesManager)
        productsAdapter.setHasStableIds(true)
        recView.adapter = productsAdapter
        recView.setHasFixedSize(true)

        back.setOnClickListener{
            ProductsList.price = cartDataManager.price
            ProductsList.productsList = cartDataManager.productsList
            ProductsList.Update = true
            finish()
        }

        cancel.setOnClickListener{
            editText.text = null
            alias.visibility = View.VISIBLE
            cancel.visibility = View.INVISIBLE
            cancel.isClickable = false
            editText.layoutParams = textEmpty
            recView.visibility = View.INVISIBLE
            recView.isClickable = false
        }

        editText.addTextChangedListener{
            if(editText.text.toString() != ""){
                alias.visibility = View.INVISIBLE
                cancel.visibility = View.VISIBLE
                cancel.isClickable = true
                editText.layoutParams = textFull
                recView.visibility = View.VISIBLE
                recView.isClickable = true

                val list = filter_data(editText.text.toString())
                if(list.isEmpty()){
                    alias.text = "Ничего не нашлось :("
                    alias.visibility = View.VISIBLE
                    recView.visibility = View.INVISIBLE
                    recView.isClickable = false
                }
                else{
                    productsAdapter.setList(list)
                }
            }
            else{
                alias.text = "Введите название блюда, \n" + "которое ищете"
                alias.visibility = View.VISIBLE
                alias.visibility = View.VISIBLE
                cancel.visibility = View.INVISIBLE
                cancel.isClickable = false
                editText.layoutParams = textEmpty
                recView.visibility = View.INVISIBLE
                recView.isClickable = false
            }
        }
    }
}