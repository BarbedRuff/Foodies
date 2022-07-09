package com.example.foodies

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class ViewPagerAdapter(fragmentActivity: FragmentActivity, pM: ProductsManager, cM: CategoriesManager, caM: CartManager, cdm: CartDataManager, context: View) : FragmentStateAdapter(fragmentActivity) {
    var productsManager: ProductsManager
    var categoriesManager: CategoriesManager
    var cartDataManager: CartDataManager
    var cartManager: CartManager
    var ctx: View
    init {
        productsManager = pM
        categoriesManager = cM
        cartManager = caM
        cartDataManager = cdm
        ctx = context
    }
    override fun createFragment(position: Int): Fragment {
        return CardFragment.newInstance(position, productsManager, categoriesManager, cartManager, cartDataManager, ctx)
    }

    override fun getItemCount(): Int {
        return categoriesManager.categories.size
    }
}