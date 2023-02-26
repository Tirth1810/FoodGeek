package com.example.foofgeek

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.foofgeek.Helper.DBHelper

class CategoryFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_category, container, false)
        val listView = view.findViewById<ListView>(R.id.listVeiw_category)
        val dbHelper = DBHelper(context, "Recipes.db", null, 1)
        val categoryList = dbHelper.recipes_SelectCategory()
        listView.adapter =
            category_adapter(this.context, categoryList, R.layout.fragment_category_item)
        listView.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            val selectCategory = categoryList!![position]
            val intent = Intent(activity, RecipeListActivity::class.java)
            intent.putExtra("category", selectCategory!!.get_category())
            startActivity(intent)
        }
        return view
    }
}