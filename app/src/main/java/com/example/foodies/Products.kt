package com.example.foodies

import java.io.Serializable

class Products: Serializable {
    var id: Int = 0;
    var category_id: Int = 0;
    var name: String = ""
    var description: String = ""
    var image: String = ""
    var price_current: Int = 0
    var price_old: Int? = 0
    var measure: Int = 0
    var measure_unit: String = ""
    var energy_per_100_grams: Float = 0f
    var proteins_per_100_grams: Float = 0f
    var fats_per_100_grams: Float = 0f
    var carbohydrates_per_100_grams: Float = 0f
    var tag_ids: List<Int> = listOf()
}