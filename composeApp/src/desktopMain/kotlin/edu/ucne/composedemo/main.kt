package edu.ucne.composedemo

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "DemoAp2",
    ) {
        App()
    }
}