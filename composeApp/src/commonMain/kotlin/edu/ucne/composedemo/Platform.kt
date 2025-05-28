package edu.ucne.composedemo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform