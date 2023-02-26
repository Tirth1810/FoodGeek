package com.example.foofgeek

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class TempSearchResultActivity : AppCompatActivity() {
    var sendRecipeList = ArrayList<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temp_search_result)

        //test dummy data
        sendRecipeList.add(1)
        sendRecipeList.add(2)
        sendRecipeList.add(3)
        sendRecipeList.add(4)
        val send = findViewById<Button>(R.id.btn_sendRecipeList)
        send.setOnClickListener {
            val intent = Intent(applicationContext, RecipeListActivity::class.java)
            intent.putExtra("title", "Matchgin 3 ingredients")
            intent.putIntegerArrayListExtra("list", sendRecipeList)
            startActivity(intent)
        }
    }
}