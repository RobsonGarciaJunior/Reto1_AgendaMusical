package com.example.agenda_musical_reto1

import BaseActivity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agenda_musical_reto1.data.Song
import com.example.agenda_musical_reto1.data.repository.remote.RemoteSongDataSource
import com.example.agenda_musical_reto1.data.repository.remote.RemoteUserDataSource
import com.example.agenda_musical_reto1.databinding.ListSongsActivityBinding
import com.example.agenda_musical_reto1.ui.viewmodels.songs.SongAdapter
import com.example.agenda_musical_reto1.ui.viewmodels.songs.SongViewModel
import com.example.agenda_musical_reto1.ui.viewmodels.songs.SongViewModelFactory
import com.example.agenda_musical_reto1.utils.MyApp
import com.example.agenda_musical_reto1.utils.Resource

class ListSongsActivity : BaseActivity() {
    private lateinit var songAdapter: SongAdapter

    private val songRepository = RemoteSongDataSource()
    private val userRepository = RemoteUserDataSource()
    private var favoritesLoaded = false

    private val songViewModel: SongViewModel by viewModels {
        SongViewModelFactory(
            songRepository,
            userRepository
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //TODO CORREGIR NOSE PORQUE TE LOGUEA COMO EL USUARIO 4 YA DE PRIMERAS EN LA APP
        super.onCreate(savedInstanceState)
        val binding = ListSongsActivityBinding.inflate(layoutInflater)
        binding.songRecycler.layoutManager = LinearLayoutManager(this)

        setContentView(binding.root)

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
        findViewById<EditText>(R.id.songFilter).addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                val searchTerm = charSequence.toString()
                songViewModel.onGetFilteredSongs(searchTerm)
            }

            override fun afterTextChanged(editable: Editable?) {}
        })


        Spinner.setupPopupMenu(spinnerButton, this)
        songAdapter = SongAdapter(::onSongListClickItem, ::onLikeClick)
        binding.songRecycler.adapter = songAdapter

        songViewModel.songs.observe(this, Observer {
            Log.e("PruebasDia1", "ha ocurrido un cambio en la lista total")
            when (it.status) {
                Resource.Status.SUCCESS -> {

                    if (!favoritesLoaded) {
                        songViewModel.getFavoriteSongs()
                        favoritesLoaded = true
                    }

                    if (!it.data.isNullOrEmpty()) {
                        // val listOfFavorite: List<Song>? = songViewModel.favoriteSongs.value?.data
                        // checkIfFavorite(it.data, listOfFavorite)
                        songAdapter.submitList(it.data)
                        songAdapter.notifyDataSetChanged()
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

        songViewModel.favoriteSongs.observe(this, Observer {
            Log.e("PruebasDia1", "ha ocurrido un cambio en la lista filtrada")
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    songViewModel.updateSongListWithFavorites()
                }

                Resource.Status.ERROR -> {
                    // Toast.makeText(this, it.message ?: "Error desconocido", Toast.LENGTH_LONG).show()
                    Log.e("ListSongsActivity", "Error al cargar datos: ${it.message}")
                }

                Resource.Status.LOADING -> {
                    Log.d("ListSongsActivity", "Cargando datos...")
                }
            }

        })
        songViewModel.filteredSongs.observe(this, Observer {
            Log.e("PruebasDia1", "ha ocurrido un cambio en la lista filtrada")
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    if (!it.data.isNullOrEmpty()) {
                        songAdapter.submitList(it.data)
                        songAdapter.notifyDataSetChanged()
                        Log.d("ListSongsActivity", "Datos cargados correctamente: ${it.data}")
                    } else {
                        Log.d("ListSongsActivity", "El autor indicado no tiene canciones")
                    }
                }

                Resource.Status.ERROR -> {
                    Toast.makeText(this, it.message ?: "Error desconocido", Toast.LENGTH_LONG).show()
                    Log.e("ListSongsActivity", "Error al cargar datos: ${it.message}")
                }

                Resource.Status.LOADING -> {
                    Log.d("ListSongsActivity", "Cargando datos...")
                }
            }

        })

        songViewModel.createdFavorite.observe(this, Observer {
            Log.e("PruebasDia1", "ha ocurrido add en la lista de favs")

            if (it != null) {
                when (it.status) {
                    Resource.Status.SUCCESS -> {

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

        songViewModel.deletedFavorite.observe(this, Observer {
            Log.e("PruebasDia1", "ha ocurrido un delete en la lista de favs")

            if (it != null) {
                when (it.status) {
                    Resource.Status.SUCCESS -> {

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

    private fun onLikeClick(song: Song) {
        if (MyApp.userPreferences.getLoggedUser() == null) {
            Toast.makeText(this, "Debes Iniciar Sesion para añadir canciones a favoritas", Toast.LENGTH_LONG).show()
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
