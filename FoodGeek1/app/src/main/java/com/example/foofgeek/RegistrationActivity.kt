package com.example.foofgeek

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.foofgeek.Helper.DBHelper
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeButtonEnabled(true)
        val tv_enterAcc = findViewById<TextView>(R.id.tv_enterAcc)
        val tv_enterPass = findViewById<TextView>(R.id.tv_enterPass)
        val tv_repeatPass = findViewById<TextView>(R.id.tv_repeatPass)
        val etRegAcc = findViewById<EditText>(R.id.etRegAcc)
        val etRegPass = findViewById<EditText>(R.id.etRegPass)
        val etRegRepeatPass = findViewById<EditText>(R.id.etRegRepeatPass)
        val bRegSubmit = findViewById<Button>(R.id.bRegSubmit)
        bRegSubmit.setOnClickListener { v ->
            val username = etRegAcc.text.toString()
            val password = etRegPass.text.toString()
            val password2 = etRegRepeatPass.text.toString()
            val accLength = username.length
            val passLength = password.length
            val dbHelper = DBHelper(v.context, "Recipes.db", null, 1)
            if (accLength > 3) {
                if (accLength < 19) {
                    if (passLength > 3) {
                        if (passLength < 19) {
                            if (password == password2) {
                                if (dbHelper.user_IsUsernameFree(username)) {
                                    dbHelper.user_Insert(username, sha256(password))
                                    Toast.makeText(
                                        v.context,
                                        "Registration Complete!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    tv_enterPass.setTextColor(Color.BLACK)
                                    tv_enterAcc.setTextColor(Color.BLACK)
                                    tv_repeatPass.setTextColor(Color.BLACK)
                                } else {
                                    Toast.makeText(
                                        v.context,
                                        "This username is already exist!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    tv_enterPass.setTextColor(Color.BLACK)
                                    tv_enterAcc.setTextColor(Color.RED)
                                    tv_repeatPass.setTextColor(Color.BLACK)
                                }
                            } else {
                                Toast.makeText(v.context, "Password mismatch!", Toast.LENGTH_SHORT)
                                    .show()
                                tv_enterPass.setTextColor(Color.BLACK)
                                tv_enterAcc.setTextColor(Color.BLACK)
                                tv_repeatPass.setTextColor(Color.RED)
                            }
                        } else {
                            Toast.makeText(
                                v.context,
                                "Password can't be more than 18 symbols!",
                                Toast.LENGTH_SHORT
                            ).show()
                            tv_enterPass.setTextColor(Color.RED)
                            tv_repeatPass.setTextColor(Color.BLACK)
                            tv_enterAcc.setTextColor(Color.BLACK)
                        }
                    } else {
                        Toast.makeText(
                            v.context,
                            "Password must be at least 4 symbols!",
                            Toast.LENGTH_SHORT
                        ).show()
                        tv_enterPass.setTextColor(Color.RED)
                        tv_repeatPass.setTextColor(Color.BLACK)
                        tv_enterAcc.setTextColor(Color.BLACK)
                    }
                } else {
                    Toast.makeText(
                        v.context,
                        "Account name can't be more than 18 symbols!",
                        Toast.LENGTH_SHORT
                    ).show()
                    tv_enterPass.setTextColor(Color.BLACK)
                    tv_repeatPass.setTextColor(Color.BLACK)
                    tv_enterAcc.setTextColor(Color.RED)
                }
            } else {
                Toast.makeText(
                    v.context,
                    "Account name must be at least 4 symbols!",
                    Toast.LENGTH_SHORT
                ).show()
                tv_enterAcc.setTextColor(Color.RED)
                tv_enterPass.setTextColor(Color.BLACK)
                tv_repeatPass.setTextColor(Color.BLACK)
            }
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

    companion object {
        fun sha256(password: String): String {
            return try {
                val digest = MessageDigest.getInstance("SHA-256")
                val hash = digest.digest(password.toByteArray(StandardCharsets.UTF_8))
                val hexString = StringBuffer()
                for (i in hash.indices) {
                    val hex = Integer.toHexString(0xff and hash[i].toInt())
                    if (hex.length == 1) hexString.append('0')
                    hexString.append(hex)
                }
                hexString.toString()
            } catch (ex: Exception) {
                throw RuntimeException(ex)
            }
        }
    }
}