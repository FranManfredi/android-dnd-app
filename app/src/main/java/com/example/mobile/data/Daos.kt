package com.example.mobile.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

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

@Dao
interface CharacterDao {
    @Query("SELECT * FROM character")
    fun getAll(): List<Character>

    @Query("SELECT * FROM character WHERE name = :name")
    fun getByName(name: String): Character?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(character: Character)

    @Update
    fun update(character: Character)

    @Delete
    fun delete(character: Character)
}

@Dao
interface BaseStatsDao {
    @Query("SELECT * FROM base_stats WHERE id = :id")
    fun getById(id: Int): BaseStats?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(baseStats: BaseStats)

    @Update
    fun update(baseStats: BaseStats)

    @Delete
    fun delete(baseStats: BaseStats)
}

@Dao
interface CharacterHpDao {
    @Query("SELECT * FROM character_hp WHERE id = :id")
    fun getById(id: Int): CharacterHp?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(characterHp: CharacterHp)

    @Update
    fun update(characterHp: CharacterHp)

    @Delete
    fun delete(characterHp: CharacterHp)
}

@Dao
interface CharacterProficiencyDao {
    @Query("SELECT * FROM character_proficiency WHERE id = :id")
    fun getById(id: Int): CharacterProficiency?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(proficiency: CharacterProficiency)

    @Update
    fun update(proficiency: CharacterProficiency)

    @Delete
    fun delete(proficiency: CharacterProficiency)
}

@Dao
interface CharacterClassDao {
    @Query("SELECT * FROM character_class WHERE characterName = :characterName")
    fun getByCharacterName(characterName: String): List<CharacterClass>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(characterClass: CharacterClass)

    @Update
    fun update(characterClass: CharacterClass)

    @Delete
    fun delete(characterClass: CharacterClass)
}

@Dao
interface TraitDao {
    @Query("SELECT * FROM trait WHERE characterName = :characterName")
    fun getByCharacterName(characterName: String): List<Trait>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(trait: Trait)

    @Update
    fun update(trait: Trait)

    @Delete
    fun delete(trait: Trait)
}

@Dao
interface CharacterWithDetailsDao {
    @Transaction
    @Query("SELECT * FROM character WHERE name = :characterName")
    fun getCharacterWithDetails(characterName: String): CharacterWithDetails?
}
