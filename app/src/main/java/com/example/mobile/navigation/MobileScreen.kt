package com.example.mobile.navigation

enum class MobileScreen {
    Home,

    Compendium,
    Creator,

    Settings,
}

val basePages = listOf(
    MobileScreen.Home.name,
)