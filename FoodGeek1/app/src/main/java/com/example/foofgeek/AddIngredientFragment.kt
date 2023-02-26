package com.example.foofgeek

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView.OnItemClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment

class AddIngredientFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_ingredient, container, false)
        retainInstance = true
        var txt_ingredient: EditText? = null
        var list_ingredient: ListView? = null
        var btn_ingredient: Button? = null
        var ingredientList: ArrayList<String>? = null
        var inputMethodManager: InputMethodManager? = null
        //set hiding keybord
        inputMethodManager =
            this.activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        btn_ingredient = view.findViewById(R.id.btn_Add_IngredientAdd)
        txt_ingredient = view.findViewById(R.id.txt_Add_IngredientAdd)
        btn_ingredient.setOnClickListener(View.OnClickListener { view ->
            if (txt_ingredient.getText().length > 0) {
                ingredientList!!.add(txt_ingredient.getText().toString())
                txt_ingredient.setText("")
                inputMethodManager!!.hideSoftInputFromWindow(txt_ingredient.getWindowToken(), 0)
            } else {
                Toast.makeText(view.context, "Please, Insert your Ingredient", Toast.LENGTH_SHORT)
                    .show()
            }
        })
        ingredientList = ArrayList()
        list_ingredient = view.findViewById(R.id.ListView_Add_Ingredient)
        list_ingredient.setAdapter(
            AddIngredientAdapter(
                this.context,
                ingredientList!!,
                R.layout.fragment_add_ingredientitem
            )
        )
        list_ingredient.setOnItemClickListener(OnItemClickListener { parent, v, position, id ->
            ingredientList!!.removeAt(position)
            list_ingredient.setAdapter(
                AddIngredientAdapter(
                    view.context,
                    ingredientList!!,
                    R.layout.fragment_add_ingredientitem
                )
            )
        })
        return view
    }
}