package com.example.mobile.model.character

data class Character(
    val name: String,
    val level: Int,
    val race: String,
    val charClass: String,
    val background: String,
    val str: Int,
    var dex: Int,
    val con: Int,
    val int: Int,
    val wis: Int,
    val cha: Int,
    val totalHitPoints: Int,
    val currentHitPoints: Int,
)