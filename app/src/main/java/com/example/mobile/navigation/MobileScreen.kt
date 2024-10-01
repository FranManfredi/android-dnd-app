package com.example.mobile.navigation

enum class MobileScreen {
    Home,

    Compendium,
    Weapons,
    Spells,
    Races,
    Items,
    Classes,

    CompendiumCreator,

    Creator,

    Settings,
}

val basePages = listOf(
    MobileScreen.Home.name,
)