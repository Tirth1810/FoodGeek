package com.example.foofgeek

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity

class SearchResult : AppCompatActivity() {
    var listItems = ArrayList<String>()
    var gridView: GridView? = null
    var adapter: ArrayAdapter<String>? = null
    var savedReceiveMatches: ArrayList<Int>? = null
    var savedRecipeList: ArrayList<Int>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        //get the intent
        val intent = intent

        //get the resources from the intent
        val reciveRecipeList = intent.getIntegerArrayListExtra("idrecipes")
        val receiveMatches = intent.getIntegerArrayListExtra("matches")
        savedReceiveMatches = receiveMatches //saving as instance variables because I need after
        savedRecipeList = reciveRecipeList //saving as instance variables because I need after
        val receiveMatchesNoDuplicates = intent.getIntegerArrayListExtra("matchesNoDuplicates")
        val receiveMatchesNoDuplicatesString = createString(receiveMatchesNoDuplicates)
        gridView = findViewById(R.id.idGridSearchResult)
        adapter = ArrayAdapter(this, R.layout.searchresult_custom, receiveMatchesNoDuplicatesString)
        gridView.setAdapter(adapter)
        gridView.setOnItemClickListener(OnItemClickListener { parent, view, position, id -> // find the position of the view clicked
            val positionView = gridView.getPositionForView(view)
            //call the new intent to send the data
            val intent = Intent(applicationContext, RecipeListActivity::class.java)
            //send the id of the recipes related to the position of the view clicked
            intent.putExtra(
                "title", "Matching: " + Integer.toString(
                    receiveMatchesNoDuplicates!![positionView]
                ) + " ingredients"
            )
            var positions = ArrayList<Int?>()
            val valuePosition =
                receiveMatchesNoDuplicates[positionView] //the value of the ingredient
            positions =
                findPositionIdResearch(valuePosition) //find the position that matches with the array of idRecipes called savedRecipeList
            val idtosend =
                ArrayList<Int>() //create the array of id to send to the RecipeListActivity
            for (i in positions.indices) {
                idtosend.add(savedRecipeList!![positions[i]!!]) //add the value when the value of position match the value of the RecipeList saved
            }
            intent.putExtra("list", idtosend)
            startActivity(intent)
        })
    }

    /*
    Method that build the format of the string to show on the grid view
     */
    fun createString(array: ArrayList<Int>?): ArrayList<String> {
        val stringToShow = ArrayList<String>()
        for (i in array!!.indices) {
            if (array[i] == 1) {
                stringToShow.add(
                    """
    Matched
    ${array[i]}
    ingredient
    """.trimIndent()
                )
            } else {
                stringToShow.add(
                    """
    Matched
    ${array[i]}
    ingredients
    """.trimIndent()
                )
            }
        }
        return stringToShow
    }

    /*
    Method that research the position id of the matched when you have no duplicates, use to show the list of recipes related to matched ingredients
     */
    fun findPositionIdResearch(idIngredientMatched: Int): ArrayList<Int?> {
        val idResearched = ArrayList<Int?>()
        for (i in savedReceiveMatches!!.indices) {
            if (savedReceiveMatches!![i] == idIngredientMatched) {
                idResearched.add(i)
            }
        }
        return idResearched
    }
}