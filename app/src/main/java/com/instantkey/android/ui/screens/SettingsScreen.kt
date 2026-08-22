package com.instantkey.android.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

/**
 * SettingsScreen - Advanced settings configuration.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavHostController) {
    var autoKeyboardEnabled by remember { mutableStateOf(true) }
    var delayMs by remember { mutableStateOf(100f) }
    var retryCount by remember { mutableStateOf(3f) }
    var clickFallbackEnabled by remember { mutableStateOf(true) }
    var debugLoggingEnabled by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Advanced Settings",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Auto Keyboard Toggle
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Auto Keyboard")
                Switch(
                    checked = autoKeyboardEnabled,
                    onCheckedChange = { autoKeyboardEnabled = it }
                )
            }
        }

        // Delay Slider
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text("Delay: ${delayMs.toInt()} ms")
                Slider(
                    value = delayMs,
                    onValueChange = { delayMs = it },
                    valueRange = 0f..1000f,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        // Retry Count Slider
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text("Retry Count: ${retryCount.toInt()}")
                Slider(
                    value = retryCount,
                    onValueChange = { retryCount = it },
                    valueRange = 1f..5f,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        // Click Fallback Toggle
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Click Fallback")
                Switch(
                    checked = clickFallbackEnabled,
                    onCheckedChange = { clickFallbackEnabled = it }
                )
            }
        }

        // Debug Logging Toggle
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Debug Logging")
                Switch(
                    checked = debugLoggingEnabled,
                    onCheckedChange = { debugLoggingEnabled = it }
                )
            }
        }
    }
}
