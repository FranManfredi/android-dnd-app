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
    Character,

    Settings,
}

val basePages = listOf(
    MobileScreen.Home.name,
)