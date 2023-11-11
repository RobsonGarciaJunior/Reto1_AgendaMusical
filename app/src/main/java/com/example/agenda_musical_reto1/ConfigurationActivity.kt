package com.example.agenda_musical_reto1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import com.example.agenda_musical_reto1.utils.MyApp

class ConfigurationActivity : AppCompatActivity() {
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

        val spinnerButton = findViewById<ImageButton>(R.id.menuSpinner)

        mapOf(
            "Inicio" to { MenuOptionsHandler.handleMenuOption("Inicio", this) },
            "Todas las Canciones" to { MenuOptionsHandler.handleMenuOption("Todas las Canciones", this) },
            "Mis Canciones Favoritas" to { MenuOptionsHandler.handleMenuOption("Mis Canciones Favoritas", this) }
        )

        Spinner.setupPopupMenu(spinnerButton, this)

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

    private fun layoutButtonsVisibility() {
        if (MyApp.userPreferences.getLoggedUser() == null) {
            findViewById<Button>(R.id.resetPasswordButton).visibility = Button.GONE
            findViewById<Button>(R.id.deleteAccountButton).visibility = Button.GONE


            findViewById<Button>(R.id.sessionButton).text = "Iniciar Sesión"
        } else {
            findViewById<Button>(R.id.resetPasswordButton).visibility = Button.VISIBLE
            findViewById<Button>(R.id.deleteAccountButton).visibility = Button.VISIBLE
            findViewById<Button>(R.id.sessionButton).text = "Cerrar Sesión"
        }
    }
}
