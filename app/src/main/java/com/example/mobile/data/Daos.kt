package com.example.mobile.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WeaponDao {
    @Query("SELECT * FROM weapon")
    fun getAll(): List<Weapon>
    @Insert
    fun insert(weapon: Weapon)

}

@Dao
interface SpellDao {
    @Query("SELECT * FROM spell")
    fun getAll(): List<Spell>

    @Insert
    fun insert(spell: Spell)
}

@Dao
interface ItemsDao {
    @Insert
    suspend fun insert(item: Item)
    @Query("SELECT * FROM item")
    fun getAllItems(): List<Item>
}

@Dao
interface CharClassDao {
    @Query("SELECT * FROM class")
    fun getAll(): List<CharClass>

    @Insert
    fun insert(charClass: CharClass)
}

@Dao
interface RaceDao {
    @Query("SELECT * FROM race")
    fun getAll(): List<Race>

    @Insert
    fun insert(race: Race)
}
