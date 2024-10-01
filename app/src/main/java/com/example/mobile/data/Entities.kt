package com.example.mobile.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weapon")
data class Weapon(
    @PrimaryKey() val name: String,
    val damage: String,
    val to_hit: Int,
    val image_url: String,
    val damage_type: String,
    val description: String,
)

@Entity(tableName = "spell")
data class Spell(
    @PrimaryKey() val name: String,
    val level: Int,
    val damage: String,
    val damage_type: String,
    val description: String,
    val casting_time: String,
)

@Entity(tableName = "item")
data class Item(
    @PrimaryKey() val name: String,
    val type: String,
    val charges: String,
    val recharge: String,
    val description: String
)

@Entity(tableName = "class")
data class CharClass (
    @PrimaryKey() val name: String,
    val hit_die: String,
    val description: String,
    val saving_throws: String,
    val primary_ability: String
)

@Entity(tableName = "race")
data class Race (
    @PrimaryKey() val name: String,
    val size: String,
    val speed: Int,
    val description: String,
    val special_abilities: String
)

