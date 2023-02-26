package com.example.foofgeek

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class AddIngredientAdapter(context: Context?, ingredientList: ArrayList<String>, layout: Int) :
    BaseAdapter() {
    private val inflater: LayoutInflater
    private val ingredientList: ArrayList<String>
    private val layout: Int

    init {
        inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        this.ingredientList = ingredientList
        this.layout = layout
    }

    override fun getCount(): Int {
        return ingredientList.size
    }

    override fun getItem(position: Int): Any {
        return ingredientList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false)
        }
        val ingredientItem = ingredientList[position]
        val name = convertView.findViewById<TextView>(R.id.txt_ingredient)
        name.text = ingredientItem
        return convertView
    }
}