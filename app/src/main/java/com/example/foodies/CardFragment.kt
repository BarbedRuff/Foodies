package com.example.foodies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView


class CardFragment(pM: ProductsManager, cM: CategoriesManager, caM: CartManager, pos: Int?, cdm: CartDataManager, context: View) : Fragment() {
    private var counter: Int? = null
    var cartDataManager: CartDataManager
    var productsManager: ProductsManager
    var categoriesManager: CategoriesManager
    var cartManager: CartManager
    var position: Int = 0
    lateinit var ctx: View
    init {
        productsManager = pM
        categoriesManager = cM
        cartManager = caM
        cartDataManager = cdm
        ctx = context
        if (pos != null) {
            position = pos
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            counter = requireArguments().getInt(ARG_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment , container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val products = view.findViewById<RecyclerView>(R.id.recProducts)
        val productsAdapter = ProductsAdapter()
        productsAdapter.setHasStableIds(true)
        products.adapter = productsAdapter
        products.setHasFixedSize(true)
        productsAdapter.setList(productsManager.getProductsByCategoryId(categoriesManager.categories[position].id), cartManager,cartDataManager, categoriesManager, view)
        if(CountProduct.scrollpos == -1){
            products.scrollToPosition(ProductsList.scrollPos)
            ProductsList.scrollPos = -1
        }
        else{
            products.scrollToPosition(CountProduct.scrollpos)
            CountProduct.scrollpos = -1
        }
    }

    companion object {
        private const val ARG_COUNT = "param1"
        fun newInstance(counter: Int?, productsManager: ProductsManager, categoriesManager: CategoriesManager, cartManager: CartManager, cdm: CartDataManager, context: View): CardFragment {
            val fragment = CardFragment(productsManager, categoriesManager, cartManager, counter, cdm, context)
            val args = Bundle()
            args.putInt(ARG_COUNT, counter!!)
            fragment.arguments = args
            return fragment
        }
    }
}