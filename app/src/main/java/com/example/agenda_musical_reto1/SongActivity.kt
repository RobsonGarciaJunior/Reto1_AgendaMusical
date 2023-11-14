package com.example.agenda_musical_reto1

import BaseActivity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.agenda_musical_reto1.data.Song
import com.example.agenda_musical_reto1.data.repository.remote.RemoteSongDataSource
import com.example.agenda_musical_reto1.data.repository.remote.RemoteUserDataSource
import com.example.agenda_musical_reto1.ui.viewmodels.users.UserViewModel
import com.example.agenda_musical_reto1.ui.viewmodels.users.UserViewModelFactory
import com.example.agenda_musical_reto1.utils.MyApp
import com.example.agenda_musical_reto1.utils.Resource
import com.example.agenda_musical_reto1.utils.YouTubeThumbnailUtil


class SongActivity : BaseActivity() {

    private val userRepository = RemoteUserDataSource()
    private val songRepository = RemoteSongDataSource()
    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory(
            userRepository,
            songRepository
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.song_activity)
        val spinnerButton = findViewById<ImageButton>(R.id.menuSpinner)
        Spinner.setupPopupMenu(spinnerButton, this)

        val receivedSong: Song? = intent.getParcelableExtra("song")
        //val receivedSong: Song? = intent.extras?.getString("song") as Song?

        if (receivedSong != null) {
            findViewById<TextView>(R.id.song).text = receivedSong.title
            findViewById<TextView>(R.id.author).text = receivedSong.author
            val icon = findViewById<ImageView>(R.id.songIcon)
            YouTubeThumbnailUtil.loadYouTubeThumbnail(this, receivedSong.url, icon)
            if (receivedSong.isFavorite) {
                findViewById<ImageButton>(R.id.favorite_heart).setImageResource(R.drawable.ic_favorite)
            } else {
                findViewById<ImageButton>(R.id.favorite_heart).setImageResource(R.drawable.ic_not_favorite)
            }
        }

        findViewById<ImageButton>(R.id.favorite_heart).setOnClickListener {
            if (receivedSong != null) {
                if (MyApp.userPreferences.getLoggedUser() == null) {
                    Toast.makeText(this, "Debes Iniciar Sesion para annadir canciones a favoritas", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    if (receivedSong.isFavorite) {
                        receivedSong.isFavorite = false
                        userViewModel.onDeleteFavorite(receivedSong.idSong!!)
                    } else {
                        receivedSong.isFavorite = true
                        userViewModel.onCreateFavorite(receivedSong.idSong!!)
                    }
                }
            }
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
        userViewModel.createdFavorite.observe(this, Observer {
            Log.e("PruebasDia1", "ha ocurrido add en la lista de favs")

            if (it != null) {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        findViewById<ImageButton>(R.id.favorite_heart).setImageResource(R.drawable.ic_favorite)
                    }

                    Resource.Status.ERROR -> {
                        Toast.makeText(this, it.message ?: "Error desconocido", Toast.LENGTH_LONG)
                            .show()
                        Log.e("ListSongsActivity", "Error al cargar datos: ${it.message}")
                    }

                    Resource.Status.LOADING -> {
                        Log.d("ListSongsActivity", "Cargando datos...")
                    }
                }
            }
        })
        userViewModel.deletedFavorite.observe(this, Observer {
            Log.e("PruebasDia1", "ha ocurrido un delete en la lista de favs")

            if (it != null) {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        findViewById<ImageButton>(R.id.favorite_heart).setImageResource(R.drawable.ic_not_favorite)
                    }

                    Resource.Status.ERROR -> {
                        Toast.makeText(this, it.message ?: "Error desconocido", Toast.LENGTH_LONG)
                            .show()
                        Log.e("ListSongsActivity", "Error al cargar datos: ${it.message}")
                    }

                    Resource.Status.LOADING -> {
                        Log.d("ListSongsActivity", "Cargando datos...")
                    }
                }
            }
        })


    }

    private fun openYouTubeLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }



}
