package com.example.foodies.CartPackage

import com.example.foodies.Products

class CartProduct(products: MutableList<Products>, productsList: MutableList<Pair<Int, Int>>){
    var list: MutableList<CartItem> = mutableListOf()
    init{
        for(i in productsList){
            var it = products.filter{it.id == i.first}
            list.add(CartItem(i.first, it[0].name, i.second, it[0].price_current, it[0].price_old))
        }
    }
}