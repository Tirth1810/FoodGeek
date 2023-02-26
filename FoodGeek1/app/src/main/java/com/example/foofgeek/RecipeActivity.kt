package com.example.foofgeek

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.foofgeek.Helper.DBHelper
import com.example.foofgeek.Helper.ImageHelper
import com.example.foofgeek.Model.RecipeItem

class RecipeActivity : AppCompatActivity() {
    var imageHelper = ImageHelper()
    var recipeItem: RecipeItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        //Connect DB
        val dbHelper = DBHelper(this, "Recipes.db", null, 1)

        //Show backbutton
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeButtonEnabled(true)

        //set userid
        val pref = getSharedPreferences("Login", MODE_PRIVATE)
        val userID = pref.getString("userID", "")

        //connect with view
        val recipeName = findViewById<TextView>(R.id.txt_recipeName)
        val author = findViewById<TextView>(R.id.txt_recipeAuthor)
        val uploadDate = findViewById<TextView>(R.id.txt_recipeUploaddate)
        val country = findViewById<TextView>(R.id.txt_recipeCountry)
        val ingredients = findViewById<TextView>(R.id.txt_recipeIngredients)
        val description = findViewById<TextView>(R.id.txt_recipeDescription)
        val howto = findViewById<TextView>(R.id.txt_recipeHowto)
        val mainImg = findViewById<ImageView>(R.id.img_recipeMainImg)
        val like = findViewById<CheckBox>(R.id.chk_recipeLike)

        //set for received data
        val intent = intent
        val name = intent.getStringExtra("recipe")
        //set to selected recipe data
        recipeItem = dbHelper.recipes_SelectByName(name)
        if (recipeItem != null) {
            //data bind
            mainImg.setImageBitmap(imageHelper.getBitmapFromByteArray(recipeItem!!._mainImg))
            recipeName.text = recipeItem!!._recipeName
            author.text = recipeItem!!._author
            uploadDate.text = recipeItem!!._uploadDate
            country.text = recipeItem!!._category
            description.text = recipeItem!!._Description
            howto.text = recipeItem!!._howTo
            val ingredentList = dbHelper.ingredients_SelectByRecipeId(
                recipeItem!!._id
            )
            var tempIngre: String? = ""
            for (i in ingredentList!!.indices) {
                tempIngre += ingredentList[i]
                if (i != ingredentList.size - 1) tempIngre += " / "
            }
            ingredients.text = tempIngre
        } else {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
        }
        if (userID !== "") {
            like.isChecked = dbHelper.like_GetLikeYNByUserId(userID, recipeItem!!._id)
        }
        like.setOnCheckedChangeListener { buttonView, isChecked ->
            //check again userid
            val pref = getSharedPreferences("Login", MODE_PRIVATE)
            val userID = pref.getString("userID", "")
            if (userID === "") {
                //connect to login page
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
                buttonView.isChecked = false
            } else {
                if (isChecked) {
                    val count = dbHelper.recipes_AddLike(userID, recipeItem!!._id)
                } else {
                    val count = dbHelper.recipes_MinusLike(userID, recipeItem!!._id)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}