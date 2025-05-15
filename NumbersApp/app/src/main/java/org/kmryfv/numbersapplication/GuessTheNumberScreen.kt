package org.kmryfv.numbersapplication

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuessTheNumberScreen(onBackToMenu: () -> Unit, modifier: Modifier = Modifier) {
    var userInput by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var secretNumber by remember { mutableStateOf(Random.nextInt(1, 11)) }
    var isGuessedCorrectly by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Volver") },
                navigationIcon = {
                    IconButton(onClick = onBackToMenu) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding) // deja espacio para la barra superior
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

            Button(
                onClick = {
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