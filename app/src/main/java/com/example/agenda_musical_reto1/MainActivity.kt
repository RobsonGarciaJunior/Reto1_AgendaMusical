package com.example.agenda_musical_reto1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.agenda_musical_reto1.utils.MyApp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val rememberMe = MyApp.userPreferences.isRememberMeEnabled()
        val loggedUser = MyApp.userPreferences.getLoggedUser()

        if (rememberMe || loggedUser != null) {
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
            finish()
            return
        }


        findViewById<TextView>(R.id.guest_link).setOnClickListener() {
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
            finish()
        }

        findViewById<Button>(R.id.register_view_button).setOnClickListener() {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        findViewById<Button>(R.id.login_button).setOnClickListener() {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
    
}


