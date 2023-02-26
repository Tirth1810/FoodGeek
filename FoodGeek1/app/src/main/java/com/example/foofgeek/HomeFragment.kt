package com.example.foofgeek

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.GridView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.foofgeek.Helper.DBHelper
import com.example.foofgeek.Helper.ImageHelper
import com.example.foofgeek.Model.RecipeItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import java.util.*

class HomeFragment : Fragment() {
    var imageHelper = ImageHelper()
    var bestList: ArrayList<RecipeItem>? = null
    var newList: ArrayList<RecipeItem>? = null
    var today = Date()
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val fab = view.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { //Check search recipes menu on the slide menu
            val navigationView = activity!!.findViewById<NavigationView>(R.id.nav_view)
            navigationView.setCheckedItem(R.id.nav_search)
            //connect to search recipes page
            val manager = activity!!.supportFragmentManager
            val searchFragment = SearchFragment()
            manager.beginTransaction().replace(R.id.root_layout, searchFragment, searchFragment.tag)
                .addToBackStack(null).commit()
        }
        val newGridView = view.findViewById<GridView>(R.id.GridView_New)

        //Connect DB
        val dbHelper = DBHelper(context, "Recipes.db", null, 1)
        val defaultDataList = dbHelper.recipes_SelectAll()
        if (defaultDataList == null || defaultDataList.size == 0) {
            //Set Bibimap data
            var drawable = resources.getDrawable(R.drawable.bibimbap, activity!!.theme)
            val bibimbap = imageHelper.getByteArrayFromDrawable(drawable)
            dbHelper.recipes_Insert(
                "Korea", "Bibimbap", "shyjoo", today.toString(),
                "1. rice \n 2. hubs and egg \n 3. minx", "Korean traditional food",
                bibimbap, bibimbap, 0
            )
            val bibmbapId = dbHelper.recipes_GetIdByName("Bibimbap")
            val bibmbapIngre = ArrayList<String>()
            bibmbapIngre.add("rice")
            bibmbapIngre.add("egg")
            bibmbapIngre.add("sesame oil")
            bibmbapIngre.add("gochujang")
            bibmbapIngre.add("carrot")
            for (i in bibmbapIngre.indices) {
                dbHelper.ingredients_Insert(bibmbapId, bibmbapIngre[i])
            }

            //Set Bulgogi data
            drawable = resources.getDrawable(R.drawable.bulgogi, activity!!.theme)
            val bulgogi = imageHelper.getByteArrayFromDrawable(drawable)
            dbHelper.recipes_Insert(
                "Korea",
                "Bulgogi",
                "shyjoo",
                today.toString(),
                """1. Thinly slice 1 pound of sirloin or tenderloin beef against the grain.
2. Mix these ingredients to make a marinade:
	 2 tbs of soy sauce
	 3 tbs of water
	 1 tbs of brown sugar
	 1 tbs of honey
	 1 tbs of sesame oil
	 1 tbs of toasted sesame seeds
	 2 chopped green onions
	 4 cloves of minced garlic
	 ½ ts of black pepper.
3. Add the beef to the marinade and keep in the fridge at least 30 minutes. If your cut of beef is tough, you can marinate longer to soften it, or use a Korean pear in the marinade, like I do in this recipe.
4. Cook it on a pan or a grill, and transfer to a plate or a cast iron plate to serve.
5. Sprinkle chopped green onion and toasted sesame seeds over top.
6. Wrap a piece of bulgogi in a lettuce left with a little bit of ssamjang, and put it in your mouth. You can dip carrot or cucumber strips into the ssamjang.""",
                "Korean traditional food",
                bulgogi,
                bulgogi,
                4
            )
            val bulgogiId = dbHelper.recipes_GetIdByName("Bulgogi")
            val bulgogiIngre = ArrayList<String>()
            bulgogiIngre.add("soy sauce")
            bulgogiIngre.add("brown sugar")
            bulgogiIngre.add("sesame oil")
            bulgogiIngre.add("gochujang")
            bulgogiIngre.add("green onion")
            bulgogiIngre.add("onion")
            for (i in bulgogiIngre.indices) {
                dbHelper.ingredients_Insert(bulgogiId, bulgogiIngre[i])
            }

            //Set Bolognese data
            drawable = resources.getDrawable(R.drawable.bolognese, activity!!.theme)
            val bolognese = imageHelper.getByteArrayFromDrawable(drawable)
            dbHelper.recipes_Insert(
                "Itay", "Bolognese", "shyjoo", today.toString(),
                """
                    1. Put the onion and oil in a large pan and fry over a fairly high heat for 3-4 mins. Add the garlic and mince and fry until they both brown. Add the mushrooms and herbs, and cook for another couple of mins.
                    2. Stir in the tomatoes, beef stock, tomato ketchup or purée, Worcestershire sauce and seasoning. Bring to the boil, then reduce the heat, cover and simmer, stirring occasionally, for 30 mins.
                    3. Meanwhile, cook the spaghetti in a large pan of boiling, salted water, according to packet instructions. Drain well, run hot water through it, put it back in the pan and add a dash of olive oil, if you like, then stir in the meat sauce. Serve in hot bowls and hand round Parmesan cheese, for sprinkling on top.
                    """.trimIndent(),
                "Make our traditional spaghetti Bolognese recipe with homemade Bolognese sauce and tender chunks of beef, making this dish a family favourite.",
                bolognese, bolognese, 6
            )
            val bologneseId = dbHelper.recipes_GetIdByName("Bolognese")
            val bologneseIngre = ArrayList<String>()
            bologneseIngre.add("onion")
            bologneseIngre.add("olive oil")
            bologneseIngre.add("spaghetti")
            bologneseIngre.add("Parmesan")
            bologneseIngre.add("mushroom")
            bologneseIngre.add("oregano")
            bologneseIngre.add("tomatoes")
            bologneseIngre.add("beef stock")
            bologneseIngre.add("beef")
            for (i in bologneseIngre.indices) {
                dbHelper.ingredients_Insert(bologneseId, bologneseIngre[i])
            }

            //Set Chicken Cacciatore data
            drawable = resources.getDrawable(R.drawable.chickencacciatore, activity!!.theme)
            val chickencacciatore = imageHelper.getByteArrayFromDrawable(drawable)
            dbHelper.recipes_Insert(
                "Itay", "Chicken Cacciatore ", "shyjoo", today.toString(),
                """
                    1. Combine the flour, salt and pepper in a plastic bag. Shake the chicken pieces in flour until coated. Heat the oil in a large skillet (one that has a cover/lid). Fry the chicken pieces until they are browned on both sides. Remove from skillet.
                    2. Add the onion, garlic and bell pepper to the skillet and saute until the onion is slightly browned. Return the chicken to the skillet and add the tomatoes, oregano and wine. Cover and simmer for 30 minutes over medium low heat.
                    3. Add the mushrooms and salt and pepper to taste. Simmer for 10 more minutes.
                    """.trimIndent(),
                "Many food names reflect various occupations or trades.",
                chickencacciatore, chickencacciatore, 2
            )
            val chickencacciatoreId = dbHelper.recipes_GetIdByName("Chicken Cacciatore ")
            val chickencacciatoreIngre = ArrayList<String>()
            chickencacciatoreIngre.add("flour")
            chickencacciatoreIngre.add("salt")
            chickencacciatoreIngre.add("black pepper")
            chickencacciatoreIngre.add("chicken")
            chickencacciatoreIngre.add("vegetable oil")
            chickencacciatoreIngre.add("onion")
            chickencacciatoreIngre.add("tomatoes")
            chickencacciatoreIngre.add("oregano")
            chickencacciatoreIngre.add("wine")
            for (i in chickencacciatoreIngre.indices) {
                dbHelper.ingredients_Insert(chickencacciatoreId, chickencacciatoreIngre[i])
            }

            //Set abzhorka data
            drawable = resources.getDrawable(R.drawable.abzhorka, activity!!.theme)
            val abzhorka = imageHelper.getByteArrayFromDrawable(drawable)
            dbHelper.recipes_Insert(
                "Russia", "Abzhorka", "shyjoo", today.toString(),
                """
                    1. Put the onion and oil in a large pan and fry over a fairly high heat for 3-4 mins. Add the garlic and mince and fry until they both brown. Add the mushrooms and herbs, and cook for another couple of mins.
                    2. Stir in the tomatoes, beef stock, tomato ketchup or purée, Worcestershire sauce and seasoning. Bring to the boil, then reduce the heat, cover and simmer, stirring occasionally, for 30 mins.
                    3. Meanwhile, cook the spaghetti in a large pan of boiling, salted water, according to packet instructions. Drain well, run hot water through it, put it back in the pan and add a dash of olive oil, if you like, then stir in the meat sauce. Serve in hot bowls and hand round Parmesan cheese, for sprinkling on top.
                    """.trimIndent(),
                "Make our traditional spaghetti Bolognese recipe with homemade Bolognese sauce and tender chunks of beef, making this dish a family favourite.",
                abzhorka, abzhorka, 3
            )
            val abzhorkaId = dbHelper.recipes_GetIdByName("Abzhorka")
            val abzhorkaIngre = ArrayList<String>()
            abzhorkaIngre.add("carrot")
            abzhorkaIngre.add("pickle")
            abzhorkaIngre.add("onion")
            abzhorkaIngre.add("beef")
            for (i in abzhorkaIngre.indices) {
                dbHelper.ingredients_Insert(abzhorkaId, abzhorkaIngre[i])
            }

            //Set beefstroganoff data
            drawable = resources.getDrawable(R.drawable.beefstroganoff, activity!!.theme)
            val beefstroganoff = imageHelper.getByteArrayFromDrawable(drawable)
            dbHelper.recipes_Insert(
                "Russia", "Beef Stroganoff", "shyjoo", today.toString(),
                "Chop the meat long wise fibers (fibres) and beat the pieces a little. After that cut the pieces into stripes 2 cm long and 1/2 cm wide. Season and roll them in flour. Fry chopped onion in the pan and when it is gold brown, put the stripes there. Fry on hot heat until the meat is light brown. Make a sauce: fry 1 tb flour pounded with butter for few minutes, add sour cream, ketchup, salt. Pour the sauce over meat and stew on a low heat during 15-20 minutes. Don't let sauce to boil, overwise the meat will be hard. Beef Stroganoff is served with fried potatoes.\n",
                "Beef stroganoff is a dish consisting of strips of lean beef sauteed and served in a sour-cream sauce with onions and mushrooms. Legend has it that when he was stationed in deepest Siberia, his chef discovered that the beef was frozen so solid that it could only be coped with by cutting it into very thin strips.",
                beefstroganoff, beefstroganoff, 8
            )
            val beefstroganoffId = dbHelper.recipes_GetIdByName("Beef Stroganoff")
            val beefstroganoffIngre = ArrayList<String>()
            beefstroganoffIngre.add("beef")
            beefstroganoffIngre.add("flour")
            beefstroganoffIngre.add("ketchup")
            beefstroganoffIngre.add("sour cream")
            beefstroganoffIngre.add("broth")
            beefstroganoffIngre.add("onion")
            for (i in beefstroganoffIngre.indices) {
                dbHelper.ingredients_Insert(beefstroganoffId, beefstroganoffIngre[i])
            }
        }
        val resultTextView = view.findViewById<TextView>(R.id.txt_DBresult)

        newList = dbHelper.recipes_SelectNew()
        newGridView.adapter =
            MainRecipeAdapter(this.context, newList, R.layout.fragment_home_recipeitem)
        newGridView.onItemClickListener = OnItemClickListener { parent, v, position, id ->
            val selectRecipe = newList!!.get(position)
            val intent = Intent(activity, RecipeActivity::class.java)
            intent.putExtra("recipe", selectRecipe!!.get_recipeName())
            startActivity(intent)
        }

        //connect GrieView code to UI
        val bestGridView = view.findViewById<GridView>(R.id.GridView_Best)
        bestList = dbHelper.recipes_SelectBest()
        bestGridView.adapter =
            MainRecipeAdapter(this.context, bestList, R.layout.fragment_home_recipeitem)
        bestGridView.onItemClickListener = OnItemClickListener { parent, v, position, id ->
            val selectRecipe = bestList!!.get(position)
            val intent = Intent(activity, RecipeActivity::class.java)
            intent.putExtra("recipe", selectRecipe!!.get_recipeName())
            startActivity(intent)
        }
        return view
    }
}