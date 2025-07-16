package com.example.cancionero.settings

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.cancionero.MainActivity
import com.example.cancionero.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        // Opcional: Puedes agregar cualquier lógica adicional aquí, como la carga de datos iniciales o la inicialización de servicios.

        // Ejemplo: Espera 2 segundos y luego abre la siguiente actividad
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish() // Cierra la actividad de splash para evitar que el usuario la vea al usar el botón "back"
        }, 5000) // 2000 milisegundos = 2 segundos
    }
}
