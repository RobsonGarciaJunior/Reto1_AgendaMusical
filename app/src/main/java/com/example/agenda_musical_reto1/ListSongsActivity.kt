package com.example.agenda_musical_reto1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agenda_musical_reto1.data.Song
import com.example.agenda_musical_reto1.data.repository.remote.RemoteSongDataSource
import com.example.agenda_musical_reto1.data.repository.remote.RemoteUserDataSource
import com.example.agenda_musical_reto1.databinding.ListSongsActivityBinding
import com.example.agenda_musical_reto1.ui.viewmodels.songs.SongAdapter
import com.example.agenda_musical_reto1.ui.viewmodels.songs.SongViewModel
import com.example.agenda_musical_reto1.ui.viewmodels.songs.SongViewModelFactory
import com.example.agenda_musical_reto1.ui.viewmodels.users.UserViewModel
import com.example.agenda_musical_reto1.ui.viewmodels.users.UserViewModelFactory
import com.example.agenda_musical_reto1.utils.MyApp
import com.example.agenda_musical_reto1.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ListSongsActivity : AppCompatActivity() {
    private lateinit var songAdapter: SongAdapter
    private val songRepository = RemoteSongDataSource()
    private val songViewModel: SongViewModel by viewModels {
        SongViewModelFactory(
            songRepository
        )
    }

    private val userRepository = RemoteUserDataSource()
    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory(
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

        if (intent.extras?.getString("actualIntent").equals("Todas las Canciones")) {
            findViewById<TextView>(R.id.listSongTypeLabel).text = "Todas las Canciones"
            //Aqui es necesario crear una corrutina para que asegurarnos de cargar primero la lista de favoritas para no comparar una lista nula
            runBlocking {
                val job: Job = launch(context = Dispatchers.Default) {
                    songViewModel.updateSongList()
                }
                //TODO REVISAR PORQUE AQUI CARGA LAS DE ALGUN USUARIO SI NO SE ESTA LOGUEADO
                userViewModel.getFavoriteSongs()
                job.join()
            }
        } else if (intent.extras?.getString("actualIntent").equals("Mis Canciones Favoritas")) {
            findViewById<TextView>(R.id.listSongTypeLabel).text = "Mis Canciones Favoritas"
            userViewModel.getFavoriteSongs()
        }
        Spinner.setupPopupMenu(spinnerButton, this)
        songAdapter = SongAdapter(::onSongListClickItem, ::onLikeClick)
        binding.songRecycler.adapter = songAdapter

        songViewModel.songs.observe(this, Observer {
            Log.e("PruebasDia1", "ha ocurrido un cambio en la lista total")
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    if (!it.data.isNullOrEmpty()) {
                        val listOfFavorite: List<Song>? = userViewModel.favoriteSongs.value?.data
                        checkIfFavorite(it.data, listOfFavorite)
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

        userViewModel.favoriteSongs.observe(this, Observer {
            Log.e("PruebasDia1", "ha ocurrido un cambio en la lista de favs")

            when (it.status) {
                Resource.Status.SUCCESS -> {
                    if (!it.data.isNullOrEmpty()) {
                        for (song in it.data) {
                            song.isFavorite = true
                        }
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
        userViewModel.createdFavorite.observe(this, Observer {
            Log.e("PruebasDia1", "ha ocurrido add en la lista de favs")

            if (it != null) {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        songViewModel.updateSongList()
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
                        songViewModel.updateSongList()
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

    private fun checkIfFavorite(data: List<Song>, listOfFavorite: List<Song>?) {
        for (song in data) {
            for (favorite in listOfFavorite!!) {
                if (song.idSong == favorite.idSong) {
                    song.isFavorite = true
                }
            }
        }
    }

    private fun onSongListClickItem(song: Song) {
        val intent = Intent(this, SongActivity::class.java)
        intent.putExtra("song", song)
        startActivity(intent)
        finish()
    }

    private fun onLikeClick(song: Song) {
        if (MyApp.userPreferences.getLoggedUser() == null) {
            Toast.makeText(this, "Debes Iniciar Sesion para annadir canciones a favoritas", Toast.LENGTH_LONG).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            if (song.isFavorite) {
                song.isFavorite = false
                song.idSong?.let { userViewModel.onDeleteFavorite(it) }
            } else {
                song.isFavorite = true
                song.idSong?.let { userViewModel.onCreateFavorite(it) }
            }
        }

    }
}
