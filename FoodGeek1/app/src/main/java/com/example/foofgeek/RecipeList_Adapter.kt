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

class RecipeList_Adapter(context: Context, recipeList: ArrayList<RecipeItem>?, layout: Int) :
    BaseAdapter() {
    private val inflater: LayoutInflater
    private val recipeList: ArrayList<RecipeItem>?
    private val layout: Int
    private val imageHelper = ImageHelper()

    init {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        this.recipeList = recipeList
        this.layout = layout
    }

    override fun getCount(): Int {
        return recipeList!!.size
    }

    override fun getItem(position: Int): Any {
        return recipeList!![position]
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
        val ListView_Image = convertView.findViewById<ImageView>(R.id.listItem_image)
        ListView_Image.setImageBitmap(imageHelper.getBitmapFromByteArray(recipeItem._thumbnail))
        val listView_title = convertView.findViewById<TextView>(R.id.listItem_title)
        val listView_author = convertView.findViewById<TextView>(R.id.listItem_author)
        val listView_likecount = convertView.findViewById<TextView>(R.id.listItem_likecount)
        listView_title.text = recipeItem._recipeName
        listView_author.text = recipeItem._author
        listView_likecount.text = recipeItem._likeCount.toString()
        return convertView
    }
}