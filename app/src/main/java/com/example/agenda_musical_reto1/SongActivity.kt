package com.example.agenda_musical_reto1

import MenuOptionsHandler
import Spinner
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.agenda_musical_reto1.data.Song
import com.example.agenda_musical_reto1.utils.YouTubeThumbnailUtil
import java.util.regex.Matcher
import java.util.regex.Pattern


class SongActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.song_activity)
        val spinnerButton = findViewById<ImageButton>(R.id.menuSpinner)
        Spinner.setupPopupMenu(spinnerButton, this)

        val receivedSong: Song? = intent.getParcelableExtra("song")

        if (receivedSong != null) {
            findViewById<TextView>(R.id.song).text = receivedSong.title
            findViewById<TextView>(R.id.author).text = receivedSong.author
            val icon = findViewById<ImageView>(R.id.songIcon)
            YouTubeThumbnailUtil.loadYouTubeThumbnail(this, receivedSong.url, icon)

        }

        findViewById<ImageButton>(R.id.configButton).setOnClickListener() {
            val intent = Intent(this, ConfigurationActivity::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<ImageButton>(R.id.play_button).setOnClickListener() {
            receivedSong?.url?.let { songUrl ->
                openYouTubeLink(songUrl)
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

            Spinner.setupPopupMenu(spinnerButton, this)
        }

    }

    private fun openYouTubeLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}
