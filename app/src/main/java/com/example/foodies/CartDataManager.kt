package com.example.foodies

import java.io.Serializable

class CartDataManager(pd:ProductsManager): Serializable {
    var price: Int = 0
    var productsList: MutableList<Pair<Int, Int>> = mutableListOf()
    var productsManager: ProductsManager

    init{
        productsManager = pd
    }


    fun addPrice(summ: Int){
        price += summ
    }

    fun minusPrice(summ: Int){
        price -= summ
    }

    fun append(name_product: String){
        val products = productsManager.getProductIdByName(name_product)
        if(products != null){
            var index = -1
            for (i in productsList.indices){
                if (productsList[i].first == products.id){
                    index = i; break;
                }
            }
            if (index == -1){
                productsList.add(Pair(products.id, 1))
            }
            else{
                productsList[index] = Pair(products.id, productsList[index].second + 1)
            }
            addPrice(products.price_current)
        }
    }

    fun pop(name_product: String){
        val products = productsManager.getProductIdByName(name_product)
        if(products != null){
            var index = -1
            for (i in productsList.indices){
                if (productsList[i].first == products.id){
                    index = i; break;
                }
            }
            if(index != -1){
                if (productsList[index].second == 1){
                    productsList.removeAt(index)
                }
                else{
                    productsList[index] = Pair(productsList[index].first, productsList[index].second - 1)
                }
                minusPrice(products.price_current)
            }
        }
    }

    fun GetCountByName(name_product: String): Int{
        val products = productsManager.getProductIdByName(name_product)
        if(products != null){
            var index = -1
            for (i in productsList.indices){
                if (productsList[i].first == products.id){
                    index = i; break;
                }
            }
            if(index == -1){
                return 0
            }
            else{
                return productsList[index].second
            }
        }
        return 0
    }

    fun setPrice(){
        price = 0
        for (i in productsList){
            val p = productsManager.getProductById(i.first)
            price += p!!.price_current * i.second
        }
    }

    fun UpdateData(name_product: String, count: Int){
        val products = productsManager.getProductIdByName(name_product)
        if(products != null) {
            if (count == 0) {
                var index = -1
                for (i in productsList.indices) {
                    if (productsList[i].first == products.id) {
                        index = i; break;
                    }
                }
                if(index != -1){
                    productsList.removeAt(index)
                }
            }
            else{
                var index = -1
                for (i in productsList.indices) {
                    if (productsList[i].first == products.id) {
                        index = i; break;
                    }
                }
                if(index == -1){
                    productsList.add(Pair(products.id, count))
                }
                else{
                    productsList[index] = Pair(productsList[index].first, count)
                }
            }
            setPrice()
        }
    }

    fun setList(list: MutableList<Pair<Int, Int>>){
        productsList = list
        setPrice()
    }

}