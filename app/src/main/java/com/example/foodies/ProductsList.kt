package com.example.foodies

object ProductsList {
    var scrollPos: Int = -1
    var categoriesPos: Int = 0
    var Update: Boolean = false
    var productsList: MutableList<Pair<Int, Int>> = mutableListOf()
    var price: Int = 0
}