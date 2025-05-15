package org.kmryfv.numbersapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.kmryfv.numbersapplication.ui.theme.NumbersApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NumbersApplicationTheme {
                Surface(
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.background
                ) {
                    numbersAppNavigation()
                }
            }
        }
    }
}

@Composable
fun numbersAppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "initial") {
        composable("initial") {
            initialScreen(
                onGuessClick = { navController.navigate("guess-the-number") },
                onTableClick = { navController.navigate("multiplication-table") }
            )
        }
        composable("guess-the-number") {
            GuessTheNumberScreen(onBackToMenu = {
                navController.navigate("initial") {
                    popUpTo("initial") { inclusive = false }
                }
            })
        }
        composable("multiplication-table") {
            MultiplicationTable(onBackToMenu = {
                navController.navigate("initial") {
                    popUpTo("initial") { inclusive = false }
                }
            })
        }
    }
}


@Composable
fun initialScreen(onGuessClick: () -> Unit, onTableClick: () -> Unit,
                  modifier: Modifier = Modifier)
{
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("¡Bienvenido/a!", style = MaterialTheme.typography.headlineMedium)

        Button(
            onClick = onGuessClick,
            modifier = Modifier.fillMaxWidth())
        {
            Text("Juego: Adivina el número")
        }

        Button(
            onClick = onTableClick,
            modifier = Modifier.fillMaxWidth())
        {
            Text("Tabla de multiplicar")
        }
    }
}