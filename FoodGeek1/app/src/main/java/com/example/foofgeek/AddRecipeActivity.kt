package com.example.foofgeek

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.foofgeek.Helper.DBHelper
import com.example.foofgeek.Helper.ImageHelper
import java.util.*

class AddRecipeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)
        var mViewPager: ViewPager? = null
        var btn_addNewRecipe: Button? = null

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeButtonEnabled(true)
        var navi1: ImageView? = null
        var navi2: ImageView? = null
        var navi3: ImageView? = null

        navi1 = findViewById<View>(R.id.imgv_Add_navi1) as ImageView
        navi2 = findViewById<View>(R.id.imgv_Add_navi2) as ImageView
        navi3 = findViewById<View>(R.id.imgv_Add_navi3) as ImageView

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(
            supportFragmentManager
        )
        mSectionsPagerAdapter!!.add(AddRecipeFragment())
        mSectionsPagerAdapter!!.add(AddIngredientFragment())
        mSectionsPagerAdapter!!.add(AddHowtoFragment())

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container)
        mViewPager.setOffscreenPageLimit(3)
        mViewPager.setAdapter(mSectionsPagerAdapter)
        mViewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    navi1!!.setImageDrawable(
                        ContextCompat.getDrawable(
                            baseContext,
                            R.drawable.greenbutton
                        )
                    )
                    navi2!!.setImageDrawable(
                        ContextCompat.getDrawable(
                            baseContext,
                            R.drawable.graybackground
                        )
                    )
                    navi3!!.setImageDrawable(
                        ContextCompat.getDrawable(
                            baseContext,
                            R.drawable.graybackground
                        )
                    )
                } else if (position == 1) {
                    navi1!!.setImageDrawable(
                        ContextCompat.getDrawable(
                            baseContext,
                            R.drawable.graybackground
                        )
                    )
                    navi2!!.setImageDrawable(
                        ContextCompat.getDrawable(
                            baseContext,
                            R.drawable.greenbutton
                        )
                    )
                    navi3!!.setImageDrawable(
                        ContextCompat.getDrawable(
                            baseContext,
                            R.drawable.graybackground
                        )
                    )
                } else {
                    navi1!!.setImageDrawable(
                        ContextCompat.getDrawable(
                            baseContext,
                            R.drawable.graybackground
                        )
                    )
                    navi2!!.setImageDrawable(
                        ContextCompat.getDrawable(
                            baseContext,
                            R.drawable.graybackground
                        )
                    )
                    navi3!!.setImageDrawable(
                        ContextCompat.getDrawable(
                            baseContext,
                            R.drawable.greenbutton
                        )
                    )
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
        btn_addNewRecipe = findViewById(R.id.btn_Add_recipeAdd)

        btn_addNewRecipe.setOnClickListener(View.OnClickListener { v ->
            var newName: EditText? = null
            var newAuthor: TextView? = null
            var newCounty: Spinner? = null
            var newMainImg: ImageView? = null
            var newDescription: EditText? = null
            var newIngredientList: ListView? = null
            var newHowto: EditText? = null
            var imageHelper = ImageHelper()
            var dbHepler: DBHelper? = null

            newMainImg = findViewById(R.id.imgv_Add_Image)
            newName = findViewById(R.id.txt_Add_NewName)
            newAuthor = findViewById(R.id.txt_Add_Author)
            newCounty = findViewById(R.id.spinner_Add_Country)
            newDescription = findViewById(R.id.txt_Add_Description)
            newIngredientList = findViewById(R.id.ListView_Add_Ingredient)
            newHowto = findViewById(R.id.txt_ADD_Howto)
            val makeMainImg: ByteArray?
            val makeThumbnail: ByteArray?
            val makeRecipeName: String
            val makeDescription: String
            val makeAuthor: String
            val makeCategory: String
            val makeHowto: String
            val today = Date()
            val makeIndeList = ArrayList<String>()
            if (newMainImg.getDrawable() != null) {
                val d =
                    (findViewById<View>(R.id.imgv_Add_Image) as ImageView).drawable as BitmapDrawable
                val bitmap = d.bitmap
                val thBitmap = imageHelper.getThubmail(bitmap)
                makeMainImg = imageHelper.getByteArrayFromBitmap(bitmap)
                makeThumbnail = imageHelper.getByteArrayFromBitmap(thBitmap)
            } else {
                Toast.makeText(v.context, "Please, Pick your Picture!", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            makeRecipeName =
                if (newName.getText() != null && !newName.getText().toString().isEmpty()) {
                    newName.getText().toString()
                } else {
                    Toast.makeText(v.context, "Please, Input New Recipe Name!", Toast.LENGTH_SHORT)
                        .show()
                    return@OnClickListener
                }
            makeAuthor = newAuthor.getText().toString()
            makeCategory =
                if (newCounty.getSelectedItem() != null && !newCounty.getSelectedItem().toString()
                        .isEmpty()
                ) {
                    newCounty.getSelectedItem().toString()
                } else {
                    Toast.makeText(v.context, "Please, Select the Country!", Toast.LENGTH_SHORT)
                        .show()
                    return@OnClickListener
                }
            makeDescription =
                if (newDescription.getText() != null && !newDescription.getText().toString()
                        .isEmpty()
                ) {
                    newDescription.getText().toString()
                } else {
                    Toast.makeText(v.context, "Please, Input New Description!", Toast.LENGTH_SHORT)
                        .show()
                    return@OnClickListener
                }
            if (newIngredientList.getCount() > 0) {
                for (i in 0 until newIngredientList.getCount()) {
                    makeIndeList.add(newIngredientList.getItemAtPosition(i).toString())
                }
            } else {
                Toast.makeText(v.context, "Please, Input Your Ingredients!", Toast.LENGTH_SHORT)
                    .show()
                return@OnClickListener
            }
            makeHowto =
                if (newHowto.getText() != null && !newHowto.getText().toString().isEmpty()) {
                    newHowto.getText().toString()
                } else {
                    Toast.makeText(
                        v.context,
                        "Please, Input How to Cook this Recipe!",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@OnClickListener
                }

            //Connect DB
            val dbHelper = DBHelper(v.context, "Recipes.db", null, 1)
            dbHelper.recipes_Insert(
                makeCategory, makeRecipeName, makeAuthor, today.toString(),
                makeHowto, makeDescription,
                makeThumbnail, makeMainImg, 0
            )
            val makeRecipeid = dbHelper.recipes_GetIdByName(makeRecipeName)
            if (makeRecipeid != -1) {
                for (i in makeIndeList.indices) {
                    dbHelper.ingredients_Insert(makeRecipeid, makeIndeList[i])
                }
                Toast.makeText(v.context, "Completed to Add Your Recipe!!", Toast.LENGTH_SHORT)
                    .show()
                val intent = Intent(v.context, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(v.context, "Upload Failed ", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // NavUtils.navigateUpFromSameTask(this);
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    class PlaceholderFragment : Fragment() {
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.fragment_add_recipe, container, false)
        }

        companion object {
            private const val ARG_SECTION_NUMBER = "section_number"
            fun newInstance(sectionNumber: Int): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }

    inner class SectionsPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(
        fm!!
    ) {
        private val _fragments: ArrayList<Fragment>

        init {
            _fragments = ArrayList()
        }

        fun add(fragment: Fragment) {
            _fragments.add(fragment)
        }

        override fun getItem(position: Int): Fragment {
            return _fragments[position]
        }

        override fun getCount(): Int {
            return 3
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return "OBJECT " + (position + 1)
        }
    }

    companion object {
        private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    }
}