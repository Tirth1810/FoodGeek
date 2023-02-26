package com.example.foofgeek;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Logout();
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        {
            public void onDrawerClosed(View view)
            {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    protected void onStart() {
        super.onStart();  // Always call the superclass method first

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        HomeFragment homeFragment = new HomeFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.root_layout, homeFragment, homeFragment.getTag()).commit();
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = drawer.isDrawerOpen(navigationView);
        super.onPrepareOptionsMenu(menu);
        SharedPreferences pref = getSharedPreferences("Login", Activity.MODE_PRIVATE);
        String userID = pref.getString("userID","");

        if (userID != "")
        {
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_add).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
            TextView userid = findViewById(R.id.txt_Menu_UserId);
            userid.setText(userID);
        }
        else
        {
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_add).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
            TextView userid = findViewById(R.id.txt_Menu_UserId);
            userid.setText("");
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager manager = getSupportFragmentManager();

        if (id == R.id.nav_home)
        {
            //connect to home page
            HomeFragment homeFragment = new HomeFragment();
            manager.beginTransaction().replace(R.id.root_layout, homeFragment, homeFragment.getTag()).addToBackStack(null).commit();
        }
        else if (id == R.id.nav_login)
        {
            //connect to login page
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_category)
        {
            //connect to category page
            CategoryFragment categoryFragment = new CategoryFragment();
            manager.beginTransaction().replace(R.id.root_layout, categoryFragment, categoryFragment.getTag()).addToBackStack(null).commit();
        }
        else if (id == R.id.nav_search)
        {
            //connect to search recipes page
            SearchFragment searchFragment = new SearchFragment();
            manager.beginTransaction().replace(R.id.root_layout, searchFragment, searchFragment.getTag()).addToBackStack(null).commit();
        }
        else if (id == R.id.nav_add)
        {
            //connect to add recipe page
             Intent intent = new Intent(this, AddRecipeActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_info)
        {
            //connect to information page
            InformationFragment informationFragment = new InformationFragment();
            manager.beginTransaction().replace(R.id.root_layout, informationFragment, informationFragment.getTag()).addToBackStack(null).commit();
        }
        else if (id == R.id.nav_logout)
        {
            Logout();
            Toast.makeText(this.getBaseContext(), "LogOut!", Toast.LENGTH_SHORT).show();
            //connect to home page
            HomeFragment homeFragment = new HomeFragment();
            manager.beginTransaction().replace(R.id.root_layout, homeFragment, homeFragment.getTag()).addToBackStack(null).commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void Logout(){
        //for share login information
        SharedPreferences pref = getSharedPreferences("Login", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        //just temporary value "shyjoo"
        editor.clear();
        editor.commit();
    }
}