package com.example.agenda_musical_reto1

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.agenda_musical_reto1.data.repository.remote.RemoteSongDataSource
import com.example.agenda_musical_reto1.data.repository.remote.RemoteUserDataSource
import com.example.agenda_musical_reto1.ui.viewmodels.users.UserViewModel
import com.example.agenda_musical_reto1.ui.viewmodels.users.UserViewModelFactory
import com.example.agenda_musical_reto1.utils.Resource
import com.example.agenda_musical_reto1.utils.ValidationUtils

class RegisterActivity : AppCompatActivity() {
    private val userRepository = RemoteUserDataSource()
    private val songRepository = RemoteSongDataSource()
    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory(
            userRepository,
            songRepository
        )
    }

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)

        findViewById<ImageButton>(R.id.configButton).setOnClickListener() {
            val intent = Intent(this, ConfigurationActivity::class.java)
            startActivity(intent)
            finish()
        }

        findViewById<TextView>(R.id.login_connection).setOnClickListener() {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        findViewById<Button>(R.id.register_button).setOnClickListener() {
            val formEmail = findViewById<TextView>(R.id.email_text_register).text.toString()
            val formName = findViewById<TextView>(R.id.name_text).text.toString()
            val formSurname = findViewById<TextView>(R.id.surname_text).text.toString()
            val formPassword = findViewById<TextView>(R.id.password_text_register).text.toString()
            val formPasswordRepeat =
                findViewById<TextView>(R.id.password_repeat_register_text).text.toString()

            if (ValidationUtils.isEmailValid(formEmail)) {
                if (ValidationUtils.arePasswordsMatching(
                        formPassword,
                        formPasswordRepeat
                    ) and ValidationUtils.passwordLength(
                        formPassword
                    )
                ) {
                    if (ValidationUtils.areAllFieldsFilled(
                            formEmail,
                            formPassword,
                            formPasswordRepeat,
                            formName,
                            formSurname
                        )
                    ) {
                        userViewModel.onUserRegister(
                            formName,
                            formSurname,
                            formEmail,
                            formPassword
                        )


                    } else {
                        findViewById<TextView>(R.id.email_text_register).error =
                            "Rellena todos los campos"
                    }
                } else {
                    findViewById<TextView>(R.id.password_repeat_register_text).error =
                        "Las contraseñas no coinciden o es demasiado corta"
                }

            } else {
                findViewById<TextView>(R.id.email_text_register).error =
                    "Formato del Email incorrecto"
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
        userViewModel.created.observe(this, Observer {
            if (it != null) {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        val formEmail =
                            findViewById<TextView>(R.id.email_text_register).text.toString()
                        val formPass =
                            findViewById<TextView>(R.id.password_text_register).text.toString()
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.putExtra("email", formEmail)
                        intent.putExtra("password", formPass)
                        startActivity(intent)
                        finish()
                    }

                    Resource.Status.ERROR -> {
                        findViewById<TextView>(R.id.email_text_register).error = "Email en uso"
                    }

                    Resource.Status.LOADING -> {
                        // de momento
                    }
                }
            }

            //
        })

        Spinner.setupPopupMenu(spinnerButton, this)
    }
}
