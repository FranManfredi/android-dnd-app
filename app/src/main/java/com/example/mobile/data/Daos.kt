package com.example.mobile.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ItemsDao {
    @Insert
    suspend fun insert(item: Item)
    @Update
    suspend fun update(item: Item)
    @Delete
    suspend fun delete(item: Item)
    @Query("SELECT * FROM item")
    fun getAllItems(): List<Item>

}