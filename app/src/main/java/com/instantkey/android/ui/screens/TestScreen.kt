package com.instantkey.android.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import com.instantkey.android.util.AccessibilityUtils
import com.instantkey.android.util.KeyboardUtils

/**
 * TestScreen - Test keyboard and accessibility functionality.
 */
@Composable
fun TestScreen(navController: NavHostController) {
    val context = LocalContext.current
    val statusMessage = remember { mutableStateOf("Ready to test") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            "Test Keyboard",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Test Status
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Info, contentDescription = "Status")
                Spacer(modifier = Modifier.width(8.dp))
                Text(statusMessage.value)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Test Buttons
        Button(
            onClick = { KeyboardUtils.openInputMethodSettings(context) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Text("Open Keyboard Settings")
        }

        Button(
            onClick = { AccessibilityUtils.openAccessibilitySettings(context) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Text("Open Accessibility Settings")
        }

        Button(
            onClick = { statusMessage.value = "Keyboard show requested" },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Text("Attempt Keyboard Show")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            "Debug Information",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 16.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(
                "Package: com.instantkey.android\nVersion: 1.0.0\n" +
                "Accessibility: Check settings\nKeyboard: Check settings",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}
