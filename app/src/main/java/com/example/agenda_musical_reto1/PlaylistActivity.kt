package com.example.agenda_musical_reto1

import BaseActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agenda_musical_reto1.data.Song
import com.example.agenda_musical_reto1.data.repository.remote.RemoteSongDataSource
import com.example.agenda_musical_reto1.data.repository.remote.RemoteUserDataSource
import com.example.agenda_musical_reto1.databinding.PlaylistActivityBinding
import com.example.agenda_musical_reto1.ui.viewmodels.songs.SongAdapter
import com.example.agenda_musical_reto1.ui.viewmodels.songs.SongViewModel
import com.example.agenda_musical_reto1.ui.viewmodels.songs.SongViewModelFactory
import com.example.agenda_musical_reto1.utils.MyApp
import com.example.agenda_musical_reto1.utils.Resource

class PlaylistActivity : BaseActivity() {
    private lateinit var songAdapter: SongAdapter

    private val userRepository = RemoteUserDataSource()
    private val songRepository = RemoteSongDataSource()
    private val songViewModel: SongViewModel by viewModels {
        SongViewModelFactory(
            songRepository,
            userRepository
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = PlaylistActivityBinding.inflate(layoutInflater)
        binding.songRecycler.layoutManager = LinearLayoutManager(this)
        setContentView(binding.root)
        val receivedArtist = intent.getStringExtra("artist")
        val playlistLabel = findViewById<TextView>(R.id.playlistLabel)
        Log.d( "artista", receivedArtist.toString())
        if (receivedArtist != null) {
            val formattedText = getString(R.string.playlist_label, receivedArtist)
            playlistLabel.text = formattedText

            songViewModel.onGetPlaylistSongs(receivedArtist)
        }




        findViewById<ImageButton>(R.id.configButton).setOnClickListener() {
            val intent = Intent(this, ConfigurationActivity::class.java)
            startActivity(intent)
            finish()
        }


        val spinnerButton = findViewById<ImageButton>(R.id.menuSpinner)

        mapOf("Inicio" to { MenuOptionsHandler.handleMenuOption("Inicio", this) },
            "Todas las Canciones" to {
                MenuOptionsHandler.handleMenuOption(
                    "Todas las Canciones", this
                )
            },
            "Mis Canciones Favoritas" to {
                MenuOptionsHandler.handleMenuOption(
                    "Mis Canciones Favoritas", this
                )
            })

        Spinner.setupPopupMenu(spinnerButton, this)
        songAdapter = SongAdapter(::onSongListClickItem, ::onLikeClick)
        binding.songRecycler.adapter = songAdapter

        songViewModel.playlistsongs.observe(this, Observer {
            Log.e("PruebasDia1", "ha ocurrido un cambio en la lista total")
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    if (!it.data.isNullOrEmpty()) {
                        val list: List<Song>? = songViewModel.playlistsongs.value?.data
                        checkIfFavorite(it.data, list)
                        songAdapter.submitList(it.data)
                        Log.d("ListSongsActivity", "Datos cargados correctamente: ${it.data}")
                    } else {
                        Log.d("ListSongsActivity", "La lista de canciones está vacía")
                    }
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
        })


        songViewModel.deletedFavorite.observe(this, Observer {
            Log.e("PruebasDia1", "ha ocurrido un delete en la lista de favs")

            if (it != null) {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        songViewModel.getFavoriteSongs()
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

    private fun onSongListClickItem(song: Song) {
        val intent = Intent(this, SongActivity::class.java)
        intent.putExtra("song", song)
        startActivity(intent)
        finish()
    }
    private fun checkIfFavorite(data: List<Song>, listOfFavorite: List<Song>?) {
        if(!listOfFavorite.isNullOrEmpty()){
            for (song in data) {
                for (favorite in listOfFavorite!!) {
                    if (song.idSong == favorite.idSong) {
                        song.isFavorite = true
                    }
                }
            }
        }

    }

    private fun onLikeClick(song: Song) {
        if (MyApp.userPreferences.getLoggedUser() == null) {
            Toast.makeText(
                this,
                "Debes Iniciar Sesion para añadir canciones a favoritas",
                Toast.LENGTH_LONG
            ).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            if (song.isFavorite) {
                song.idSong?.let { songViewModel.onDeleteFavorite(it) }
            } else {
                song.idSong?.let { songViewModel.onCreateFavorite(it) }
            }
        }

    }
}
