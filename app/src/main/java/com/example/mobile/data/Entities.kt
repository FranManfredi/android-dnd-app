package com.example.mobile.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
data class Item(
    @PrimaryKey() val name: String,
    val type: String,
    val charges: String,
    val recharge: String,
    val description: String
)