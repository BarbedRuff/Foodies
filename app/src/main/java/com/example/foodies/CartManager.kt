package com.example.foodies

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout

class CartManager(
    contextC: Context,
    textView: TextView,
    prL: RelativeLayout,
    rVi: ViewPager2,
    cL: TabLayout,
    cdm: CartDataManager,
    cartButton: RelativeLayout
){
    val cartBtn: RelativeLayout
    var cartDataManager: CartDataManager
    var priceText: TextView
    var prLayout: RelativeLayout
    var categoriesLayout: TabLayout
    var recView: ViewPager2
    var cartFull: RelativeLayout.LayoutParams
    var cartEmpty: RelativeLayout.LayoutParams
    var context: Context
    init{
        cartDataManager = cdm
        context = contextC
        priceText = textView
        prLayout = prL
        recView = rVi
        categoriesLayout = cL
        cartBtn = cartButton
        cartFull = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        cartFull.topMargin = (context.resources.displayMetrics.density * 16).toInt()
        cartFull.bottomMargin = 0
        cartFull.addRule(RelativeLayout.ABOVE, cartButton.id)
        cartFull.addRule(RelativeLayout.BELOW, categoriesLayout.id)

        cartEmpty = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        cartEmpty.topMargin = (context.resources.displayMetrics.density * 16).toInt()
        cartEmpty.bottomMargin = 0
        cartEmpty.addRule(RelativeLayout.ABOVE, 0)
        cartEmpty.addRule(RelativeLayout.BELOW, categoriesLayout.id)
    }

    @SuppressLint("SetTextI18n")
    fun getSum() {
        if (cartDataManager.price == 0) {
            prLayout.visibility = View.INVISIBLE
            cartBtn.isClickable = false
            recView.setLayoutParams(cartEmpty)
        }
        priceText.text = "${cartDataManager.price.toString()} \u20bd"
    }

    fun append(name_product: String){
        prLayout.visibility = View.VISIBLE
        cartBtn.isClickable = true
        if(cartDataManager.productsList.isEmpty()){
            recView.setLayoutParams(cartFull)
        }
        cartDataManager.append(name_product)
        getSum()
    }

    fun pop(name_product: String){
        cartDataManager.pop(name_product)
        getSum()
    }

    fun Update(){
        if(cartDataManager.productsList.isEmpty()){
            prLayout.visibility = View.INVISIBLE
            cartBtn.isClickable = false
            recView.setLayoutParams(cartEmpty)
        }
        else{
            prLayout.visibility = View.VISIBLE
            cartBtn.isClickable = true
            recView.setLayoutParams(cartFull)
        }
        getSum()
    }
}