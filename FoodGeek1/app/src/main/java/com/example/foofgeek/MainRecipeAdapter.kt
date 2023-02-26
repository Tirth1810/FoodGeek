package com.example.foofgeek

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.foofgeek.Helper.ImageHelper
import com.example.foofgeek.Model.RecipeItem

class MainRecipeAdapter(context: Context?, recipeList: java.util.ArrayList<RecipeItem>?, layout: Int) :
    BaseAdapter() {
    private val inflater: LayoutInflater
    private val recipeList: ArrayList<RecipeItem>?
    private val layout: Int
    private val imageHelper = ImageHelper()

    init {
        inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        this.recipeList = recipeList
        this.layout = layout
    }

    override fun getCount(): Int {
        return recipeList!!.size
    }

    override fun getItem(position: Int): Any {
        return recipeList!![position]!!.get_recipeName()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false)
        }
        val recipeItem = recipeList!![position]
        val icon = convertView.findViewById<ImageView>(R.id.img_mainListItem)
        icon.setImageBitmap(imageHelper.getBitmapFromByteArray(recipeItem!!.get_thumbnail()))
        val name = convertView.findViewById<TextView>(R.id.txt_mainListItem)
        name.text = recipeItem!!._recipeNam
        return convertView
    }
}