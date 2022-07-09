package com.example.foodies

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

class Product : Activity() {
    var id = 0
    var count = 0
    var position = 0
    var scrollpos = 0
    lateinit var name:String

    companion object{
        const val ID = "id";
        const val DESC = "desc";
        const val NAME = "name";
        const val MEASURE = "measure";
        const val ENERGY = "energy";
        const val PRICE = "price";
        const val PROTEINS = "proteins";
        const val FATS_PER = "fats_per";
        const val CARB = "carbohydrates_per_100_grams";
        const val COUNT = "count";
        const val POS = "position";
        const val SCROLL = "pos_rec";
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        init()
    }

    override fun onBackPressed() {
        CountProduct.name = name
        CountProduct.count = count
        CountProduct.update = true
        CountProduct.categoriesPos = position
        CountProduct.scrollpos = scrollpos
        finish()
    }

    fun init(){
        id = intent.getIntExtra(ID, 0);
        name = intent.getStringExtra(NAME).toString();
        val price = intent.getStringExtra(PRICE);
        val desc = intent.getStringExtra(DESC);
        val measure = intent.getStringExtra(MEASURE);
        val energy = intent.getStringExtra(ENERGY);
        val proteins = intent.getStringExtra(PROTEINS);
        val fats_per = intent.getStringExtra(FATS_PER);
        val carb = intent.getStringExtra(CARB);
        position = intent.getIntExtra(POS, 0)
        count = intent.getIntExtra(COUNT, 0);
        scrollpos = intent.getIntExtra(SCROLL, 0);
        val card_name = findViewById<TextView>(R.id.name)
        card_name.text = name.toString()
        val card_desc = findViewById<TextView>(R.id.description)
        card_desc.text = desc.toString()
        val Measure = findViewById<TextView>(R.id.measure)
        Measure.text = measure.toString()
        val Energy = findViewById<TextView>(R.id.energy)
        Energy.text = energy.toString()
        val Proteins = findViewById<TextView>(R.id.proteins)
        Proteins.text = proteins.toString()
        val Fast_Per = findViewById<TextView>(R.id.fats_per)
        Fast_Per.text = fats_per.toString()
        val Carb = findViewById<TextView>(R.id.carbohydrates_per)
        Carb.text = carb.toString()
        val gg = findViewById<TextView>(R.id.centerText)
        gg.text = "В корзину за ${price.toString()}"
        val back = findViewById<ImageView>(R.id.back)
        back.setOnClickListener{
            CountProduct.name = name
            CountProduct.count = count
            CountProduct.update = true
            CountProduct.categoriesPos = position
            CountProduct.scrollpos = scrollpos
            finish()
        }

        val rladd = findViewById<RelativeLayout>(R.id.addToCart)
        val rlcounter = findViewById<RelativeLayout>(R.id.counter)
        val minus = findViewById<ImageView>(R.id.minus)
        val plus = findViewById<ImageView>(R.id.plus)
        val countView = findViewById<TextView>(R.id.count)

        if(count != 0){
            rladd.visibility = View.INVISIBLE
            rladd.isClickable = false
            rlcounter.visibility = View.VISIBLE
            minus.isClickable = true
            plus.isClickable = true
            countView.text = count.toString()
        }
        else{
            rladd.visibility = View.VISIBLE
            rladd.isClickable = true
            rlcounter.visibility = View.INVISIBLE
            minus.isClickable = false
            plus.isClickable = false
            countView.text = "1"
        }
        rladd.setOnClickListener{
            count = 1
            rladd.visibility = View.INVISIBLE
            rladd.isClickable = false
            rlcounter.visibility = View.VISIBLE
            minus.isClickable = true
            plus.isClickable = true
            countView.text = count.toString()
        }
        minus.setOnClickListener{
            count--
            countView.text = count.toString()
            if(count == 0){
                rladd.visibility = View.VISIBLE
                rladd.isClickable = true
                rlcounter.visibility = View.INVISIBLE
                minus.isClickable = false
                plus.isClickable = false
                countView.text = "1"
            }
        }
        plus.setOnClickListener{
            count++
            countView.text = count.toString()
        }
    }
}