package com.example.agenda_musical_reto1

import BaseActivity
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import com.example.agenda_musical_reto1.utils.MyApp

class MainMenuActivity : BaseActivity() {
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
            val intent = Intent(this, PlaylistActivity::class.java)
            intent.putExtra("artist", findViewById<TextView>(R.id.post_malone_label).text.toString())
            startActivity(intent)
            finish()
        }
        findViewById<ImageButton>(R.id.image_button_avicii).setOnClickListener() {
            val intent = Intent(this, PlaylistActivity::class.java)
            intent.putExtra("artist", findViewById<TextView>(R.id.avicii_label).text.toString())

            startActivity(intent)
            finish()
        }
        findViewById<ImageButton>(R.id.linkin_parl_image_button).setOnClickListener() {
            val intent = Intent(this, PlaylistActivity::class.java)
            intent.putExtra("artist", findViewById<TextView>(R.id.linkin_park_label).text.toString())

            startActivity(intent)
            finish()
        }
        findViewById<ImageButton>(R.id.ACDC_image_button).setOnClickListener() {
            val intent = Intent(this, PlaylistActivity::class.java)
            intent.putExtra("artist", findViewById<TextView>(R.id.ACDC_label).text.toString())
            startActivity(intent)
            finish()
        }

        Spinner.setupPopupMenu(spinnerButton, this)
    }
}
