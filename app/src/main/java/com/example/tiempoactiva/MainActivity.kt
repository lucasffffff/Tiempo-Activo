package com.example.tiempoactiva

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tiempoactiva.ui.theme.TiempoActivaTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Variables globales
var estado: Boolean by mutableStateOf(true)  // Controla el estado del temporizador
var time: Int by mutableStateOf(0)           // Controla el tiempo transcurrido

// Clase principal de la aplicación
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configura la interfaz de usuario
        setContent {
            TiempoActivaTheme {
                // Contenedor principal de la interfaz de usuario
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Muestra el texto del tiempo transcurrido
                    Greeting(time)
                }
            }
        }

        // Lanza una corrutina para ejecutar el temporizador
        lifecycleScope.launch {
            timer()
        }
    }

    override fun onResume() {
        super.onResume()
        // Actualiza la interfaz de usuario al reanudar la actividad
        updateUI()
        // Reinicia el tiempo transcurrido al volver a la actividad
        time = 0
    }

    // Se llama cuando la actividad se pausa
    override fun onPause() {
        super.onPause()

        // Registra el tiempo actual en el registro (Log) al pausar la actividad
        Log.d("Estado", time.toString())
    }

    // Muestra un mensaje emergente (Toast) con el valor del tiempo transcurrido
    fun updateUI() {
        Toast.makeText(this, "Tiempo Activo: $time", Toast.LENGTH_LONG).show()
    }
}

// Función suspendida que aumenta 'time' en 1 segundo cada 1000 ms mientras 'estado' sea verdadero.
suspend fun timer() {
    while (estado) {
        delay(1000)
        time++
    }
}

// Composable que muestra el tiempo transcurrido
@Composable
fun Greeting(time: Int, modifier: Modifier = Modifier) {
    Text(
        text = "Tiempo activo: $time",
        style = TextStyle(fontSize = 48.sp) // Ajusta el tamaño del texto a 48sp
    )
}

// Previsualización de la interfaz de usuario
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TiempoActivaTheme {
        Greeting(time)
    }
}
