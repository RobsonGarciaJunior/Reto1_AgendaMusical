package com.example.agenda_musical_reto1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.example.agenda_musical_reto1.utils.MyApp

class ConfigurationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.configuration_activity)


        findViewById<ImageButton>(R.id.configButton).setOnClickListener() {
            val intent = Intent(this, ConfigurationActivity::class.java)
            startActivity(intent)
            finish()
        }

        findViewById<Button>(R.id.sessionButton).setOnClickListener() {
            MyApp.userPreferences.unLogUser()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        findViewById<Button>(R.id.resetPasswordButton).setOnClickListener() {
            val intent = Intent(this, ResetPasswordActivity::class.java)
            startActivity(intent)
            finish()
        }

        val spinnerButton = findViewById<ImageButton>(R.id.menuSpinner)

        mapOf(
            "Inicio" to { MenuOptionsHandler.handleMenuOption("Inicio", this) },
            "Todas las Canciones" to { MenuOptionsHandler.handleMenuOption("Todas las Canciones", this) },
            "Mis Canciones Favoritas" to { MenuOptionsHandler.handleMenuOption("Mis Canciones Favoritas", this) }
        )

        Spinner.setupPopupMenu(spinnerButton, this)
    }
}
