package ru.yakaska.materialplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.yakaska.materialplayer.presentation.screen.Screen
import ru.yakaska.materialplayer.presentation.theme.MaterialPlayerTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialPlayerTheme {

            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.MusicNote, contentDescription = null) },
                    label = { Text("Music") },
                    selected = currentDestination?.hierarchy?.any { it.route == Screen.MusicScreen.route } == true,
                    onClick = {
                        navController.navigate(Screen.MusicScreen.route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Album, contentDescription = null) },
                    label = { Text("Playlists") }, //TODO replace with string
                    selected = currentDestination?.hierarchy?.any { it.route == Screen.PlaylistScreen.route } == true,
                    onClick = {
                        navController.navigate(Screen.PlaylistScreen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.MusicScreen.route,
            Modifier.padding(innerPadding)
        ) {
            composable(Screen.MusicScreen.route) { } //TODO add screens
            composable(Screen.PlaylistScreen.route) { }
        }
    }
}
