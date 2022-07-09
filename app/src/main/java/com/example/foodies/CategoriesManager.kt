package com.example.foodies

import com.google.gson.Gson
import java.io.InputStream

class CategoriesManager(inputStream: InputStream){

    var categories: MutableList<Categories> = mutableListOf()
    init{
        val gson = Gson()
        val categoriesString = inputStream.bufferedReader().use { it.readText() }
        categories = gson.fromJson(categoriesString, Array<Categories>::class.java).toMutableList()
    }


    fun getPosById(categoryId: Int): Int {
        for(i in categories.indices){
            if(categories[i].id == categoryId){
                return i
            }
        }
        return 0
    }

}