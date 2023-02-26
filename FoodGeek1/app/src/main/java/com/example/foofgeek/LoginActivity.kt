package com.example.foofgeek

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.foofgeek.Helper.DBHelper

class LoginActivity : AppCompatActivity() {
    // EditText userName, userPassword
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Show backbutton
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeButtonEnabled(true)
        val etRegAcc = findViewById<EditText>(R.id.etRegAcc)
        val etRegPass = findViewById<EditText>(R.id.etRegPass)
        val buttonRegLogin = findViewById<Button>(R.id.bRegLogin)
        val registerLink = findViewById<TextView>(R.id.tvRegLink)
        buttonRegLogin.setOnClickListener { v ->
            val username = etRegAcc.text.toString()
            val password = etRegPass.text.toString()
            val dbHelper = DBHelper(v.context, "Recipes.db", null, 1)
            if (dbHelper.user_Login(username, RegistrationActivity.Companion.sha256(password))) {
                Toast.makeText(v.context, "Login Successful!", Toast.LENGTH_SHORT).show()
                val pref = getSharedPreferences("Login", MODE_PRIVATE)
                val editor = pref.edit()
                editor.putString("userID", username)
                editor.commit()
                val handler = Handler()
                handler.postDelayed({ finish() }, 1500)
            } else {
                Toast.makeText(v.context, "Wrong Username or Password", Toast.LENGTH_SHORT).show()
            }
        }
        //registration page access
        registerLink.setOnClickListener {
            val registerIntent = Intent(this@LoginActivity, RegistrationActivity::class.java)
            this@LoginActivity.startActivity(registerIntent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}