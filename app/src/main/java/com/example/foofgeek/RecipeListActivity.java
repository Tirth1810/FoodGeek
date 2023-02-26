package com.example.foofgeek;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foofgeek.Helper.DBHelper;
import com.example.foofgeek.Model.RecipeItem;

import java.util.ArrayList;

public class RecipeListActivity extends AppCompatActivity {

    ArrayList<RecipeItem> recipes = new ArrayList<>();
    //Connect with screen elements
    TextView txtTitle;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        //Show backbutton
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();  // Always call the superclass method first
        recipes.clear();
        //Connect with screen elements
        txtTitle = findViewById(R.id.txt_recipeListTitle);
        listView = findViewById(R.id.listview_recipelist);

        //Connect with databeas
        DBHelper dbHelper = new DBHelper(this, "Recipes.db", null, 1);

        String title = "";

        //Get the category or matching information from previous page
        Intent intent = getIntent();
        if(intent.hasExtra("category"))
        {
            title = intent.getStringExtra("category");
            //set product list title from intent key "category"
            txtTitle.setText(title);
            recipes = dbHelper.recipes_SelectByCategory(title);
        }
        else if(intent.hasExtra("title"))
        {
            title = intent.getStringExtra("title");
            //set product list title from intent key "category"
            txtTitle.setText(title);
            ArrayList<Integer> reciveRecipeList =  intent.getIntegerArrayListExtra("list");
            for (int i = 0; i < reciveRecipeList.size(); i++)
            {
                RecipeItem getRecipe = dbHelper.recipes_SelectById(reciveRecipeList.get(i));
                recipes.add(getRecipe);
            }
        }

        //set ListView with Data
        listView.setAdapter(new RecipeList_Adapter(this,recipes, R.layout.activity_recipe_list_item));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                RecipeItem selectRecipe = recipes.get(position);
                Intent intent = new Intent(getApplicationContext(), RecipeActivity.class);
                intent.putExtra("recipe", selectRecipe.get_recipeName());
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
