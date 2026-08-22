package com.instantkey.android.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.instantkey.android.accessibility.InstantKeyAccessibilityService
import com.instantkey.android.util.AccessibilityUtils
import com.instantkey.android.util.KeyboardUtils
import android.content.Context
import androidx.compose.ui.platform.LocalContext

/**
 * HomeScreen - Main status and control screen.
 */
@Composable
fun HomeScreen(navController: NavHostController) {
    val context = LocalContext.current
    val isA11yEnabled = remember {
        mutableStateOf(
            AccessibilityUtils.isAccessibilityServiceEnabled(
                context,
                InstantKeyAccessibilityService::class.java
            )
        )
    }
    val isKeyboardEnabled = remember { mutableStateOf(false) }
    val isAutoKeyboardEnabled = remember { mutableStateOf(true) }
    val delayMs = remember { mutableStateOf("100 ms") }
    val retryCount = remember { mutableStateOf("3") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        // Title
        Text(
            "InstantKey Status",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Status Cards
        StatusCard(
            title = "Accessibility Service",
            status = if (isA11yEnabled.value) "ON" else "OFF",
            icon = if (isA11yEnabled.value) Icons.Default.Check else Icons.Default.Close
        )

        StatusCard(
            title = "InstantKey Keyboard",
            status = if (isKeyboardEnabled.value) "Enabled" else "Disabled",
            icon = if (isKeyboardEnabled.value) Icons.Default.Check else Icons.Default.Close
        )

        StatusCard(
            title = "Auto Keyboard",
            status = if (isAutoKeyboardEnabled.value) "ON" else "OFF",
            icon = if (isAutoKeyboardEnabled.value) Icons.Default.Check else Icons.Default.Close
        )

        Text(
            "Delay: ${delayMs.value}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Text(
            "Retry Count: ${retryCount.value}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Warning
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.warningContainer)
        ) {
            Text(
                "Note: Android may restrict automatic keyboard display in some applications or device ROMs.",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(12.dp),
                color = MaterialTheme.colorScheme.onWarningContainer
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Buttons
        Button(
            onClick = { AccessibilityUtils.openAccessibilitySettings(context) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Text("Enable Accessibility")
        }

        Button(
            onClick = { KeyboardUtils.openInputMethodSettings(context) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Text("Enable Keyboard")
        }

        Button(
            onClick = { KeyboardUtils.openInputMethodSettings(context) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Text("Keyboard Settings")
        }

        Button(
            onClick = { AccessibilityUtils.openAccessibilitySettings(context) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Text("Accessibility Settings")
        }
    }
}

@Composable
fun StatusCard(
    title: String,
    status: String,
    icon: androidx.compose.material.icons.materialIcon
) {
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
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(title, style = MaterialTheme.typography.bodyMedium)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = title, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(status, style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}
