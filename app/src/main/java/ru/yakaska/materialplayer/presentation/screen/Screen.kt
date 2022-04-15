package ru.yakaska.materialplayer.presentation.screen

sealed class Screen(val route: String) {
    object MusicScreen : Screen("musicScreen")
    object PlaylistScreen : Screen("playlistScreen")
}