package com.example.agenda_musical_reto1

import BaseActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.agenda_musical_reto1.data.repository.remote.RemoteSongDataSource
import com.example.agenda_musical_reto1.data.repository.remote.RemoteUserDataSource
import com.example.agenda_musical_reto1.ui.viewmodels.users.UserViewModel
import com.example.agenda_musical_reto1.ui.viewmodels.users.UserViewModelFactory
import com.example.agenda_musical_reto1.utils.Resource

class LoginActivity : BaseActivity() {

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
        setContentView(R.layout.login_activity)

        if (intent.hasExtra("email") && intent.hasExtra("password")) {
            findViewById<EditText>(R.id.email_text).setText(intent.extras!!.getString("email"))
            findViewById<EditText>(R.id.email_text).setBackgroundColor(getColor(R.color.login_auto_fill_background))

            findViewById<EditText>(R.id.password_text).setText(intent.extras!!.getString("password"))
            findViewById<EditText>(R.id.password_text).setBackgroundColor(getColor(R.color.login_auto_fill_background))


        }
        if (intent.hasExtra("Toast")) {
            Toast.makeText(this, intent.getStringExtra("Toast"), Toast.LENGTH_LONG).show()
        }


        findViewById<TextView>(R.id.register_connection).setOnClickListener() {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }


        findViewById<Button>(R.id.login_button).setOnClickListener {
            val email: String = findViewById<EditText>(R.id.email_text).text.toString()
            val password: String = findViewById<EditText>(R.id.password_text).text.toString()
            val rememberMe: Boolean = findViewById<CheckBox>(R.id.remember_me).isChecked

            userViewModel.onUserLogin(email, password, rememberMe)
        }
        userViewModel.user.observe(this, Observer {
            if (it != null) {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        val intent = Intent(this, MainMenuActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                    Resource.Status.ERROR -> {
                        findViewById<EditText>(R.id.email_text).error =
                            "Usuario o Contraseña Incorrectos"
                    }

                    Resource.Status.LOADING -> {
                        // de momento
                    }
                }
            }
        })
    }
}
