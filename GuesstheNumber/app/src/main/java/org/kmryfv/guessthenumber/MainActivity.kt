package org.kmryfv.guessthenumber

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.kmryfv.guessthenumber.ui.theme.GuessTheNumberTheme
import kotlin.random.Random

/*
* Crear una aplicación en la que el usuario debe adivinar un número aleatorio
* generado por el sistema entre 1 y 10.
*
* Requisitos:
*   Mostrar un TextField para que el usuario ingrese su intento.
*   Usar un Button para comprobar si el número ingresado coincide con el número secreto.
*   Mostrar un Text con el resultado: "¡Correcto!" o "Intenta de nuevo".
*   Usar remember y mutableStateOf para manejar el estado del número secreto y la respuesta.
*   Permitir reiniciar el juego con un botón adicional.
* */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GuessTheNumberTheme {
                GameScreen()
            }
        }
    }
}

@Composable
fun GameScreen(modifier : Modifier = Modifier) {
    var userInput by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var secretNumber by remember { mutableStateOf(Random.nextInt(1, 11)) }
    var isGuessedCorrectly by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Adivina el número entre 1 y 10", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = userInput,
            onValueChange = { userInput = it },
            label = { Text("Tu intento") },
            singleLine = true
        )

        Button(onClick = {
            message = checkGuess(userInput, secretNumber)
            if (message == "¡Correcto!") {
                isGuessedCorrectly = true
            }
        },
            enabled = !isGuessedCorrectly
            ) {
            Text("Verificar")
        }

        Text(text = message)

        Button(onClick = {
            val result = resetGame()
            secretNumber = result.first
            userInput = result.second
            message = ""
            isGuessedCorrectly = false
        }) {
            Text("Reiniciar juego")
        }
    }
}

fun checkGuess(input: String, secret: Int): String {
    val guess = input.toIntOrNull()
    return when {
        guess == null -> "Por favor, ingresa un número válido"
        guess !in 1..10 -> "El número debe estar entre 1 y 10"
        guess == secret -> "¡Correcto!"
        else -> "Intenta de nuevo"
    }
}

fun resetGame(): Pair<Int, String> {
    return Pair(Random.nextInt(1, 11), "")
}