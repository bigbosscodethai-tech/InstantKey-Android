package com.instantkey.android.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.instantkey.android.accessibility.SupportedApps

/**
 * AppProfileScreen - View and manage supported applications.
 */
@Composable
fun AppProfileScreen(navController: NavHostController) {
    val supportedApps = remember { SupportedApps.getSupportedPackages() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Supported Applications",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn {
            items(supportedApps.size) { index ->
                val packageName = supportedApps.toList()[index]
                AppToggleRow(
                    appName = SupportedApps.getAppName(packageName),
                    packageName = packageName,
                    isEnabled = true
                )
            }
        }
    }
}

@Composable
fun AppToggleRow(
    appName: String,
    packageName: String,
    isEnabled: Boolean
) {
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
            Column {
                Text(appName, style = MaterialTheme.typography.bodyMedium)
                Text(packageName, style = MaterialTheme.typography.bodySmall)
            }
            Switch(checked = isEnabled, onCheckedChange = {})
        }
    }
}
