import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.agenda_musical_reto1.MainActivity
import com.example.agenda_musical_reto1.MainMenuActivity

open class BaseActivity : AppCompatActivity() {

    private var doubleBackToExitPressedOnce = false

    open fun handleSpecificBackNavigation() {
        // Verifica si la actividad actual es MainMenuActivity
        if (this is MainMenuActivity) {
            // Pregunta si quieres salir solo cuando estás en MainMenuActivity
            showExitConfirmationDialog()
        } else {
            // Si no es MainMenuActivity, simplemente finaliza la actividad actual y vuelve a MainMenuActivity
            finish()
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
        }
    }



    private fun showExitConfirmationDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Salir de la aplicación")
        alertDialogBuilder.setMessage("¿Realmente quieres salir?")
        alertDialogBuilder.setPositiveButton("Sí") { _: DialogInterface, _: Int ->
            super.onBackPressed()
        }
        alertDialogBuilder.setNegativeButton("No") { _: DialogInterface, _: Int ->
            doubleBackToExitPressedOnce = false
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }


    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        // No llames a super.onBackPressed() aquí, ya que manejarás el botón de atrás de manera personalizada
        handleSpecificBackNavigation()
    }
}
