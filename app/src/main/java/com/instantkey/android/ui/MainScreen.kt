package com.instantkey.android.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.instantkey.android.ui.screens.HomeScreen
import com.instantkey.android.ui.screens.SettingsScreen
import com.instantkey.android.ui.screens.AppProfileScreen
import com.instantkey.android.ui.screens.TestScreen

/**
 * MainScreen - Entry point for the Compose UI with navigation.
 */
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        topBar = { TopAppBar("InstantKey") },
        bottomBar = { BottomNavigation(navController) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("home") { HomeScreen(navController) }
            composable("settings") { SettingsScreen(navController) }
            composable("apps") { AppProfileScreen(navController) }
            composable("test") { TestScreen(navController) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(title: String) {
    CenterAlignedTopAppBar(
        title = { Text(title, style = MaterialTheme.typography.headlineSmall) },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
fun BottomNavigation(navController: NavHostController) {
    val currentRoute = remember { mutableStateOf("home") }

    NavigationBar(containerColor = MaterialTheme.colorScheme.primaryContainer) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = currentRoute.value == "home",
            onClick = {
                currentRoute.value = "home"
                navController.navigate("home")
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
            label = { Text("Settings") },
            selected = currentRoute.value == "settings",
            onClick = {
                currentRoute.value = "settings"
                navController.navigate("settings")
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Apps, contentDescription = "Apps") },
            label = { Text("Apps") },
            selected = currentRoute.value == "apps",
            onClick = {
                currentRoute.value = "apps"
                navController.navigate("apps")
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Build, contentDescription = "Test") },
            label = { Text("Test") },
            selected = currentRoute.value == "test",
            onClick = {
                currentRoute.value = "test"
                navController.navigate("test")
            }
        )
    }
}
