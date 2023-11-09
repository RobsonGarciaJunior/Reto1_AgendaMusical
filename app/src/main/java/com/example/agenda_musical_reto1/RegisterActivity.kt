package com.example.agenda_musical_reto1

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.example.agenda_musical_reto1.utils.ValidationUtils

class RegisterActivity : AppCompatActivity() {
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
            val formEmail = findViewById<TextView>(R.id.mail_text).text.toString()
            val formPassword = findViewById<TextView>(R.id.password_text_register).text.toString()
            val formPasswordRepeat =
                findViewById<TextView>(R.id.password_repeat_register_text).text.toString()
            if (ValidationUtils.isEmailValid(formEmail)) {
                if (ValidationUtils.arePasswordsMatching(formPassword, formPasswordRepeat)) {
                    if (ValidationUtils.areAllFieldsFilled(
                            formEmail,
                            formPassword,
                            formPasswordRepeat
                        )
                    ) {
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()

                    } else {
                        findViewById<TextView>(R.id.password_repeat_register_text).error =
                            "Rellena todos los campos"
                    }
                } else {
                    findViewById<TextView>(R.id.password_repeat_register_text).error =
                        "Las contraseñas no coinciden"
                }

            } else {
                findViewById<TextView>(R.id.mail_text).error = "Formato del Email incorrecto"
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
    }
}
