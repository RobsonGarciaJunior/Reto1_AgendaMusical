package com.example.agenda_musical_reto1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agenda_musical_reto1.data.repository.remote.RemoteSongDataSource
import com.example.agenda_musical_reto1.databinding.ListSongsActivityBinding
import com.example.agenda_musical_reto1.ui.viewmodels.songs.SongAdapter
import com.example.agenda_musical_reto1.ui.viewmodels.songs.SongViewModel
import com.example.agenda_musical_reto1.ui.viewmodels.songs.SongViewModelFactory
import com.example.agenda_musical_reto1.utils.Resource

class ListSongsActivity : AppCompatActivity() {
    private lateinit var songAdapter: SongAdapter
    private val songRepository = RemoteSongDataSource()
    private val songViewModel: SongViewModel by viewModels {
        SongViewModelFactory(
            songRepository
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {

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

        val optionActions = mapOf(
            "Inicio" to { MenuOptionsHandler.handleMenuOption("Inicio", this) },
            "Todas las Canciones" to { MenuOptionsHandler.handleMenuOption("Todas las Canciones", this) },
            "Mis Canciones Favoritas" to { MenuOptionsHandler.handleMenuOption("Mis Canciones Favoritas", this) }
        )

        Spinner.setupPopupMenu(spinnerButton, this)
        songAdapter = SongAdapter()
        binding.songRecycler.adapter = songAdapter

        songViewModel.songs.observe(this, Observer {
            Log.e("PruebasDia1", "ha ocurrido un cambio en la lista")

            when (it.status) {
                Resource.Status.SUCCESS -> {
                    if (!it.data.isNullOrEmpty()) {
                        songAdapter.submitList(it.data)
                        Log.d("ListSongsActivity", "Datos cargados correctamente: ${it.data}")
                    } else {
                        Log.d("ListSongsActivity", "La lista de canciones está vacía")
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

    }
}
