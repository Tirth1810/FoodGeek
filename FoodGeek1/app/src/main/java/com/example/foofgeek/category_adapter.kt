package com.example.foofgeek

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.foofgeek.Helper.ImageHelper
import com.example.foofgeek.Model.CategoryItem

class category_adapter(context: Context?, categoryList: ArrayList<CategoryItem>, layout: Int) :
    BaseAdapter() {
    private val inflater: LayoutInflater
    private val categoryList: ArrayList<CategoryItem?>?
    private val layout: Int
    private val imageHelper = ImageHelper()
    private val name: TextView
        get() {
            TODO()
        }

    init {
        inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        this.categoryList = categoryList
        this.layout = layout
    }

    override fun getCount(): Int {
        return categoryList!!.size
    }

    override fun getItem(position: Int): Any {
        return categoryList!![position]!!.get_category()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false)
        }
        val categoryItem = categoryList!![position]
        val icon = convertView.findViewById<ImageView>(R.id.categoryitem_img)
        val name = convertView.findViewById<TextView>(R.id.categoryitem_text)
        name.text = categoryItem!!.get_category()

        icon.setImageBitmap(imageHelper.getBitmapFromByteArray(categoryItem!!.get_mainImg()))

        return convertView
    }
}