package org.kmryfv.numbersapplication

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultiplicationTable(onBackToMenu: () -> Unit) {
    var numeroTexto by remember { mutableStateOf("") }
    var resultados by remember { mutableStateOf<List<String>>(emptyList()) }
    var error by remember { mutableStateOf<String?>(null) }

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
        Column(modifier = Modifier
            .padding(padding)
            .padding(16.dp)
        ) {
            Text("Tabla de Multiplicar", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = numeroTexto,
                onValueChange = {
                    numeroTexto = it
                    error = null
                },
                label = { Text("Número") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            error?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    val numero = numeroTexto.toIntOrNull()
                    if (numero != null) {
                        resultados = (1..12).map { "$numero x $it = ${numero * it}" }
                    } else {
                        error = "Ingresa un número válido"
                        resultados = emptyList()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver tabla")
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(resultados) { resultado ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateContentSize()
                            .shadow(2.dp, RoundedCornerShape(8.dp))
                            .background(Color(0xFFCDE7F0), RoundedCornerShape(8.dp))
                            .padding(12.dp)
                    ) {
                        Text(text = resultado, color = Color.Black)
                    }
                }
            }
        }
    }
}
