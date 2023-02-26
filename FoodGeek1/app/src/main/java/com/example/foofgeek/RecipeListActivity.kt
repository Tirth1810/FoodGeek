package com.example.foofgeek

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.foofgeek.Helper.DBHelper
import com.example.foofgeek.Model.RecipeItem

class RecipeListActivity : AppCompatActivity() {
    var recipes: ArrayList<RecipeItem?>? = ArrayList()

    //Connect with screen elements
    var txtTitle: TextView? = null
    var listView: ListView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)

        //Show backbutton
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeButtonEnabled(true)
    }

    override fun onStart() {
        super.onStart() // Always call the superclass method first
        recipes!!.clear()
        //Connect with screen elements
        txtTitle = findViewById(R.id.txt_recipeListTitle)
        listView = findViewById(R.id.listview_recipelist)

        //Connect with databeas
        val dbHelper = DBHelper(this, "Recipes.db", null, 1)
        var title: String? = ""

        //Get the category or matching information from previous page
        val intent = intent
        if (intent.hasExtra("category")) {
            title = intent.getStringExtra("category")
            //set product list title from intent key "category"
            txtTitle.setText(title)
            recipes = dbHelper.recipes_SelectByCategory(title)
        } else if (intent.hasExtra("title")) {
            title = intent.getStringExtra("title")
            //set product list title from intent key "category"
            txtTitle.setText(title)
            val reciveRecipeList = intent.getIntegerArrayListExtra("list")
            for (i in reciveRecipeList!!.indices) {
                val getRecipe = dbHelper.recipes_SelectById(reciveRecipeList[i])
                recipes!!.add(getRecipe)
            }
        }

        //set ListView with Data
        listView.setAdapter(RecipeList_Adapter(this, recipes, R.layout.activity_recipe_list_item))
        listView.setOnItemClickListener(OnItemClickListener { parent, v, position, id ->
            val selectRecipe = recipes!![position]
            val intent = Intent(applicationContext, RecipeActivity::class.java)
            intent.putExtra("recipe", selectRecipe!!._recipeName)
            startActivity(intent)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // NavUtils.navigateUpFromSameTask(this);
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}