package com.example.agenda_musical_reto1

import BaseActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.example.agenda_musical_reto1.data.repository.remote.RemoteSongDataSource
import com.example.agenda_musical_reto1.data.repository.remote.RemoteUserDataSource
import com.example.agenda_musical_reto1.ui.viewmodels.songs.SongViewModel
import com.example.agenda_musical_reto1.utils.MyApp
import com.example.agenda_musical_reto1.ui.viewmodels.users.UserViewModel
import com.example.agenda_musical_reto1.ui.viewmodels.users.UserViewModelFactory
import com.example.agenda_musical_reto1.utils.Resource

class ConfigurationActivity : BaseActivity() {
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
            setContentView(R.layout.configuration_activity)
            layoutButtonsVisibility()


            findViewById<ImageButton>(R.id.configButton).setOnClickListener() {
                val intent = Intent(this, ConfigurationActivity::class.java)
                startActivity(intent)
                finish()
            }

            findViewById<Button>(R.id.sessionButton).setOnClickListener() {
                if (MyApp.userPreferences.getLoggedUser() == null) {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    showLogoutConfirmationDialog()
                }
            }

            findViewById<Button>(R.id.resetPasswordButton).setOnClickListener() {
                val intent = Intent(this, ResetPasswordActivity::class.java)
                startActivity(intent)
                finish()
            }

            findViewById<Button>(R.id.deleteAccountButton).setOnClickListener() {
                if (MyApp.userPreferences.getLoggedUser() == null) {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    showDeleteAccountConfirmationDialog()
                }
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
            userViewModel.deleted.observe(this, Observer {

                if (it != null) {
                    when (it.status) {
                        Resource.Status.SUCCESS -> {
                            MyApp.userPreferences.unLogUser()
                            val intent = Intent(this@ConfigurationActivity, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }

                        Resource.Status.ERROR -> {
                            Toast.makeText(this, it.message ?: "Error desconocido", Toast.LENGTH_LONG)
                                .show()

                        }

                        Resource.Status.LOADING -> {
                            Log.d("ListSongsActivity", "Cargando datos...")
                        }
                    }
                }
            })

        }

        private fun showLogoutConfirmationDialog() {
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.apply {
                setMessage("¿Estás seguro de que deseas desconectarte?")
                setPositiveButton("Sí") { _, _ ->
                    MyApp.userPreferences.unLogUser()
                    val intent = Intent(this@ConfigurationActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
            }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }

        private fun showDeleteAccountConfirmationDialog() {
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.apply {
                setMessage("Una vez borrada la cuenta, se borraran todos los datos con ella. ¿Estás seguro de que deseas borrar la cuenta?")
                setPositiveButton("Sí") { _, _ ->
                    userViewModel.onDeleteUser()
                }
                setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
            }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }





        private fun layoutButtonsVisibility() {
            if (MyApp.userPreferences.getLoggedUser() == null) {
                findViewById<Button>(R.id.resetPasswordButton).visibility = Button.GONE
                findViewById<Button>(R.id.deleteAccountButton).visibility = Button.GONE
                findViewById<TextView>(R.id.welcome_label).text = "¡Bienvenido/a!"


                findViewById<Button>(R.id.sessionButton).text = "Iniciar Sesión"
            } else {
                findViewById<Button>(R.id.resetPasswordButton).visibility = Button.VISIBLE
                findViewById<Button>(R.id.deleteAccountButton).visibility = Button.VISIBLE
                findViewById<Button>(R.id.sessionButton).text = "Cerrar Sesión"
                findViewById<TextView>(R.id.welcome_label).text =
                    "¡Hola!, ${MyApp.userPreferences.getLoggedUser()?.name}"

            }
        }
    }

