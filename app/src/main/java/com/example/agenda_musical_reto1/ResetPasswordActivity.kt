package com.example.agenda_musical_reto1

import BaseActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.agenda_musical_reto1.data.repository.remote.RemoteSongDataSource
import com.example.agenda_musical_reto1.data.repository.remote.RemoteUserDataSource
import com.example.agenda_musical_reto1.ui.viewmodels.users.UserViewModel
import com.example.agenda_musical_reto1.ui.viewmodels.users.UserViewModelFactory
import com.example.agenda_musical_reto1.utils.Resource
import com.example.agenda_musical_reto1.utils.ValidationUtils

class ResetPasswordActivity : BaseActivity() {
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
        setContentView(R.layout.change_password_activity)

        findViewById<ImageButton>(R.id.configButton).setOnClickListener() {
            val intent = Intent(this, ConfigurationActivity::class.java)
            startActivity(intent)
            finish()
        }

        findViewById<Button>(R.id.change_password_button).setOnClickListener() {
            if (ValidationUtils.arePasswordsMatching(
                    findViewById<EditText>(R.id.password_text_passchng).text.toString(),
                    findViewById<EditText>(R.id.password_repeat_passchng_text).text.toString()
                )
                and ValidationUtils.passwordLength(
                    findViewById<EditText>(R.id.password_text_passchng).text.toString()
                )
            ) {

                userViewModel.onUserUpdate(
                    findViewById<EditText>(R.id.old_password_Text).text.toString(),
                    findViewById<EditText>(R.id.password_text_passchng).text.toString(),
                )
            } else {
                findViewById<EditText>(R.id.password_text_passchng).error =
                    "Las contraseñas no coinciden o la nueva contraseña es demasiado corta"

            }

        }

        val spinnerButton = findViewById<ImageButton>(R.id.menuSpinner)

        userViewModel.updated.observe(this, Observer {
            if (it != null) {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        Toast.makeText(this, "Contraseña cambiada con éxito", Toast.LENGTH_LONG)
                            .show()
                        findViewById<EditText>(R.id.old_password_Text).setText("")
                        findViewById<EditText>(R.id.password_text_passchng).setText("")
                        findViewById<EditText>(R.id.password_repeat_passchng_text).setText("")

                    }

                    Resource.Status.ERROR -> {
                        findViewById<EditText>(R.id.old_password_Text).error =
                            "La contraseña antigua no coincide"

                    }

                    Resource.Status.LOADING -> {
                        // de momento
                    }
                }
            }
        })


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
