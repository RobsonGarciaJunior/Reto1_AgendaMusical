package com.example.agenda_musical_reto1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import com.example.agenda_musical_reto1.utils.MyApp

class MainMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu_activity)

        findViewById<ImageButton>(R.id.configButton).setOnClickListener() {
            val intent = Intent(this, ConfigurationActivity::class.java)
            startActivity(intent)
            finish()
        }

        val spinnerButton = findViewById<ImageButton>(R.id.menuSpinner)

        mapOf(
            "Inicio" to { MenuOptionsHandler.handleMenuOption("Inicio", this) },
            "Todas las Canciones" to {
                MenuOptionsHandler.handleMenuOption(
                    "Todas las Canciones",
                    this
                )
            },
            "Mis Canciones Favoritas" to {
                MenuOptionsHandler.handleMenuOption(
                    "Mis Canciones Favoritas",
                    this
                )
            }

        )
        findViewById<ImageButton>(R.id.image_button_postMalone).setOnClickListener() {
            val intent = Intent(this, ListSongsActivity::class.java)
            startActivity(intent)
            finish()
        }

        Spinner.setupPopupMenu(spinnerButton, this)
    }


}
