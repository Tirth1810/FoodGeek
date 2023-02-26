package com.example.foofgeek

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var drawer: DrawerLayout? = null
    var toggle: ActionBarDrawerToggle? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logout()
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        drawer = findViewById(R.id.drawer_layout)
        toggle = object : ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        ) {
            override fun onDrawerClosed(view: View) {
                super.onDrawerClosed(view)
                invalidateOptionsMenu() // creates call to onPrepareOptionsMenu()
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                invalidateOptionsMenu() // creates call to onPrepareOptionsMenu()
            }
        }
        drawer.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onStart() {
        super.onStart() // Always call the superclass method first
        var navigationView: NavigationView? = null
        navigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        navigationView.setCheckedItem(R.id.nav_home)
        val homeFragment = HomeFragment()
        val manager = supportFragmentManager
        manager.beginTransaction().replace(R.id.root_layout, homeFragment, homeFragment.tag)
            .commit()
    }

    /* Called whenever we call invalidateOptionsMenu() */
    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        var navigationView: NavigationView? = null
        // If the nav drawer is open, hide action items related to the content view
        val drawerOpen = drawer!!.isDrawerOpen(navigationView!!)
        super.onPrepareOptionsMenu(menu)
        val pref = getSharedPreferences("Login", MODE_PRIVATE)
        val userID = pref.getString("userID", "")
        if (userID !== "") {
            navigationView!!.menu.findItem(R.id.nav_login).isVisible = false
            navigationView!!.menu.findItem(R.id.nav_add).isVisible = true
            navigationView!!.menu.findItem(R.id.nav_logout).isVisible = true
            val userid = findViewById<TextView>(R.id.txt_Menu_UserId)
            userid.text = userID
        } else {
            navigationView!!.menu.findItem(R.id.nav_login).isVisible = true
            navigationView!!.menu.findItem(R.id.nav_add).isVisible = false
            navigationView!!.menu.findItem(R.id.nav_logout).isVisible = false
            val userid = findViewById<TextView>(R.id.txt_Menu_UserId)
            userid.text = ""
        }
        return true
    }

    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId
        val manager = supportFragmentManager
        if (id == R.id.nav_home) {
            //connect to home page
            val homeFragment = HomeFragment()
            manager.beginTransaction().replace(R.id.root_layout, homeFragment, homeFragment.tag)
                .addToBackStack(null).commit()
        } else if (id == R.id.nav_login) {
            //connect to login page
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        } else if (id == R.id.nav_category) {
            //connect to category page
            val categoryFragment = CategoryFragment()
            manager.beginTransaction()
                .replace(R.id.root_layout, categoryFragment, categoryFragment.tag)
                .addToBackStack(null).commit()
        } else if (id == R.id.nav_search) {
            //connect to search recipes page
            val searchFragment = SearchFragment()
            manager.beginTransaction().replace(R.id.root_layout, searchFragment, searchFragment.tag)
                .addToBackStack(null).commit()
        } else if (id == R.id.nav_add) {
            //connect to add recipe page
            val intent = Intent(this, AddRecipeActivity::class.java)
            startActivity(intent)
        } else if (id == R.id.nav_info) {
            //connect to information page
            val informationFragment = InformationFragment()
            manager.beginTransaction()
                .replace(R.id.root_layout, informationFragment, informationFragment.tag)
                .addToBackStack(null).commit()
        } else if (id == R.id.nav_logout) {
            Logout()
            Toast.makeText(this.baseContext, "LogOut!", Toast.LENGTH_SHORT).show()
            //connect to home page
            val homeFragment = HomeFragment()
            manager.beginTransaction().replace(R.id.root_layout, homeFragment, homeFragment.tag)
                .addToBackStack(null).commit()
        }
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun Logout() {
        //for share login information
        val pref = getSharedPreferences("Login", MODE_PRIVATE)
        val editor = pref.edit()
        //just temporary value "shyjoo"
        editor.clear()
        editor.commit()
    }
}