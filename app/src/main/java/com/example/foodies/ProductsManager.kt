package com.example.foodies

import com.google.gson.Gson
import java.io.InputStream
import java.io.Serializable


class ProductsManager(inputStream: InputStream): Serializable{
    var products: MutableList<Products> = mutableListOf()
    init{
        val gson = Gson()
        val categoriesString = inputStream.bufferedReader().use { it.readText() }
        products = gson.fromJson(categoriesString, Array<Products>::class.java).toMutableList()
        for (i in products.indices){
            products[i].price_old = products[i].price_old?.div(100)
            products[i].price_current = products[i].price_current / 100
        }
    }
    fun getProductsByCategoryId(id: Int): MutableList<Products> {
        return products.filter { it.category_id == id }.toMutableList()
    }
    fun getProductIdByName(name_product: String): Products? {
        for (i in products){
            if (i.name == name_product){
                return i
            }
        }
        return null
    }
    fun getProductById(id: Int): Products? {
        for (i in products){
            if (i.id == id){
                return i
            }
        }
        return null
    }
}