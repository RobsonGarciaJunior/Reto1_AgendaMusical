package com.example.agenda_musical_reto1

import android.content.Context
import android.content.Intent
import android.view.ContextThemeWrapper
import android.widget.ImageButton
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import com.example.agenda_musical_reto1.ui.viewmodels.songs.SongViewModel
import com.example.agenda_musical_reto1.ui.viewmodels.users.UserViewModel
import com.example.agenda_musical_reto1.utils.MyApp

object MenuOptionsHandler {
    fun handleMenuOption(option: String, context: Context) {
        val intent = when (option) {
            "Inicio" -> Intent(context, MainMenuActivity::class.java)
            "Todas las Canciones" -> Intent(context, ListSongsActivity::class.java).putExtra("actualIntent","Todas las Canciones")
            "Mis Canciones Favoritas" -> if (MyApp.userPreferences.getLoggedUser() == null) {
                Intent(context, LoginActivity::class.java)
            } else {
                Intent(context, ListSongsActivity::class.java).putExtra("actualIntent","Mis Canciones Favoritas")
            }
            else -> null
        }
        intent?.run {
            context.startActivity(this)
            if (context is AppCompatActivity) {
                context.finish()
            }
        }
    }
}

class Spinner {
    companion object {
        fun setupPopupMenu(
            button: ImageButton, activity: AppCompatActivity, menuOptions: List<String> = listOf(
                "Inicio", "Todas las Canciones", "Mis Canciones Favoritas"
            )
        ) {
            val wrapper = ContextThemeWrapper(activity, R.style.PopupMenuStyle)
            val popupMenu = PopupMenu(wrapper, button)

            configureMenuOptions(popupMenu, menuOptions, activity)

            button.setOnClickListener {
                popupMenu.show()
            }
        }
        private fun configureMenuOptions(
            popupMenu: PopupMenu, menuOptions: List<String>, context: Context
        ) {
            menuOptions.forEach { option ->
                val menuItem = popupMenu.menu.add(option)
                menuItem.setOnMenuItemClickListener {
                    MenuOptionsHandler.handleMenuOption(option, context)
                    true
                }
            }
        }
    }
}
