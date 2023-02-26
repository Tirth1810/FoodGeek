package com.example.foofgeek

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.fragment.app.Fragment
import com.example.foofgeek.Helper.DBHelper

class SearchFragment : Fragment(), View.OnClickListener {
    var editText: EditText? = null
    var addButton: Button? = null
    var buttonSearch: Button? = null
    var gridView: GridView? = null

    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    var listItems = ArrayList<String>()

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE GRIDTVIEW
    var adapter: ArrayAdapter<String>? = null

    //RECORDING HOW MANY TIMES THE BUTTON HAS BEEN CLICKED
    var clickCounter = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        editText = view.findViewById(R.id.typeIngredient)
        addButton = view.findViewById(R.id.addIngredient)
        buttonSearch = view.findViewById(R.id.buttonSearch)
        gridView = view.findViewById(R.id.gridSearch)
        adapter = ArrayAdapter(context!!, R.layout.edit_text_custom_for_ingredients, listItems)
        gridView.setAdapter(adapter)

        //added a click listener on the item of the gridview
        gridView.setOnItemClickListener(OnItemClickListener { parent, view, position, id -> //delete element onclick
            val o = gridView.getItemAtPosition(position)
            listItems.removeAt(position)
            clickCounter--
            adapter!!.notifyDataSetChanged()
        })
        addButton.setOnClickListener(this)
        buttonSearch.setOnClickListener(this)
        return view
    }

    override fun onClick(v: View) {
        if (v.id == addButton!!.id) {
            if (clickCounter < 20) { //set max 20 ingredients
                if (editText!!.text.toString()
                        .trim { it <= ' ' }.length > 0
                ) {  //check if inserted a blank string
                    if (listItems.contains(editText!!.text.toString())) {
                        val toast =
                            Toast.makeText(context, "ingredient duplicated", Toast.LENGTH_SHORT)
                        toast.show()
                    } else {
                        listItems.add(editText!!.text.toString())
                        adapter!!.notifyDataSetChanged()
                        clickCounter++
                        editText!!.setText("")
                    }
                } else {
                    val toast =
                        Toast.makeText(context, "Please insert the text", Toast.LENGTH_SHORT)
                    toast.show()
                }
            } else {
                addButton!!.isClickable = false
                val toast =
                    Toast.makeText(context, "Max number of ingredients reached", Toast.LENGTH_SHORT)
                toast.show()
            }
        }
        if (v.id == buttonSearch!!.id) {
            if (listItems.isEmpty()) { //if user didin't put any ingredient display error message
                val toast =
                    Toast.makeText(context, "You didn't insert any ingredient", Toast.LENGTH_SHORT)
                toast.show()
            } else {
                //finding recipeid and count by ingredient
                val dbHelper = DBHelper(v.context, "Recipes.db", null, 1)
                val resultList = dbHelper.ingredients_selectRecipeByIngredientName(listItems)
                if (resultList!!.isEmpty()) { //if didn't find anything display error message and reccomend best recipes
                    var advices = ArrayList<Int?>()
                    advices = findBestRecipes()
                    val intent = Intent(activity, RecipeListActivity::class.java)
                    intent.putExtra(
                        "title",
                        "Sorry, we didn't match any ingredient! Take a look of our best Recipes!"
                    )
                    intent.putExtra("list", advices)
                    startActivity(intent)
                    dbHelper.close()
                } else {
                    val idRecipes = ArrayList<Int>()
                    val matches = ArrayList<Int>()


                    //filling the array of idRecipes
                    for (i in resultList.indices) {
                        idRecipes.add(resultList[i]!!._recipeId)
                    }

                    //filling the array of ingredients matches
                    for (j in resultList.indices) {
                        matches.add(resultList[j]!!._ingrCount)
                    }
                    val matchesNoDuplicates = removeDuplicates(matches)

                    //sent to Search Result intent idRecipes, matches and also the number of matches without duplicates
                    val intent = Intent(activity, SearchResult::class.java)
                    intent.putExtra("idrecipes", idRecipes)
                    intent.putExtra("matchesNoDuplicates", matchesNoDuplicates)
                    intent.putExtra("matches", matches)
                    startActivity(intent)
                    dbHelper.close()
                }
            }
        }
    }

    /*
    a function that search witch is the max in an array of int
    used for retrives the maximum of ingredients matched
    @param numMatches an array of Int that numbers of the matches
     */
    fun findMax(numMatches: ArrayList<Int>): Int {
        var maxValue = numMatches[0]
        for (i in numMatches.indices) {
            if (numMatches[i] > maxValue) {
                maxValue = numMatches[i]
            }
        }
        return maxValue
    }

    /*
   a function that search witch are the positions of the array in case of maxvalues duplicates
   used for retrives the positions of the maximum an then the id of recipes
   @param numMatches an array of Int that numbers of the matches
   return an array of int that shows the positions of the maximum
    */
    fun findPosition(matches: ArrayList<Int>, maxvalue: Int): ArrayList<Int> {
        val positions = ArrayList<Int>()
        for (i in matches.indices) {
            if (matches[i] == maxvalue) {
                positions.add(i)
            }
        }
        for (j in positions.indices) {
            println(positions[j])
        }
        return positions
    }

    /*
    this function call the db to find the best recipes and retun an arraylist of integer -> id of best recipes
     */
    fun findBestRecipes(): ArrayList<Int?> {
        val bestRecipes = ArrayList<Int?>()
        val dbHelper = DBHelper(context, "Recipes.db", null, 1)
        val results = dbHelper.recipes_SelectBest()
        if (results!!.isEmpty()) {
        } else {
            for (i in results.indices) {
                bestRecipes.add(results[i]!!._id)
            }
        }
        return bestRecipes
    }

    /*
    function that removes the duplicates in an array of Integer
     */
    fun removeDuplicates(array: ArrayList<Int>?): ArrayList<Int> {
        val noduplicates = ArrayList<Int>()
        val withoutDuplicates: Set<Int> = LinkedHashSet(array)
        noduplicates.clear()
        noduplicates.addAll(withoutDuplicates)
        return noduplicates
    }
}