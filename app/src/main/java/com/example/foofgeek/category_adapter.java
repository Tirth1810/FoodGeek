package com.example.foofgeek;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foofgeek.Helper.ImageHelper;
import com.example.foofgeek.Model.CategoryItem;
import java.util.ArrayList;


public class category_adapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<CategoryItem> categoryList;
    private int layout;
    private ImageHelper imageHelper = new ImageHelper();

    public category_adapter(Context context, ArrayList<CategoryItem> categoryList, int layout) {
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.categoryList = categoryList;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryList.get(position).get_category();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=inflater.inflate(layout,parent,false);
        }
        CategoryItem categoryItem = categoryList.get(position);
        ImageView icon= convertView.findViewById(R.id.categoryitem_img);
        icon.setImageBitmap(imageHelper.getBitmapFromByteArray(categoryItem.get_mainImg()));

        TextView name = convertView.findViewById(R.id.categoryitem_text);
        name.setText(categoryItem.get_category());
        return convertView;
    }
}