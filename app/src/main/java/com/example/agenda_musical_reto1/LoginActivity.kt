package com.example.agenda_musical_reto1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.agenda_musical_reto1.data.repository.remote.RemoteUserDataSource
import com.example.agenda_musical_reto1.ui.viewmodels.users.UserViewModel
import com.example.agenda_musical_reto1.ui.viewmodels.users.UserViewModelFactory
import com.example.agenda_musical_reto1.utils.Resource

class LoginActivity : AppCompatActivity() {

    private val userRepository = RemoteUserDataSource()

    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory(
            userRepository
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        if(intent.extras != null){
            findViewById<EditText>(R.id.email_text).setText(intent.extras!!.getString("email"))
            findViewById<EditText>(R.id.email_text).setBackgroundColor(getColor(R.color.login_auto_fill_background))

            findViewById<EditText>(R.id.password_text).setText(intent.extras!!.getString("password"))
            findViewById<EditText>(R.id.password_text).setBackgroundColor(getColor(R.color.login_auto_fill_background))

        }
        findViewById<ImageButton>(R.id.configButton).setOnClickListener() {
            val intent = Intent(this, ConfigurationActivity::class.java)
            startActivity(intent)
            finish()
        }

        findViewById<TextView>(R.id.register_connection).setOnClickListener() {
            val intent = Intent(this, RegisterActivity::class.java)
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

        findViewById<Button>(R.id.login_button).setOnClickListener {
            val email: String = findViewById<EditText>(R.id.email_text).text.toString()
            val password: String = findViewById<EditText>(R.id.password_text).text.toString()
            userViewModel.onUserLogin(email, password)
        }
        userViewModel.user.observe(this, Observer{
            if (it != null) {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        val intent = Intent(this, ListSongsActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                    Resource.Status.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }

                    Resource.Status.LOADING -> {
                        // de momento
                    }
                }
            }

            //
        })
    }
}
