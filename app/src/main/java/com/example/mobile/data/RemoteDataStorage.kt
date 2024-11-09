package com.example.mobile.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        Item::class,
        Weapon::class,
        Spell::class,
        Race::class,
        CharClass::class,
        Character::class,
        CharacterClass::class,
        BaseStats::class,
        CharacterHp::class,
        CharacterProficiency::class,
        Trait::class
    ],
    version = 10 // Incremented version number
)
abstract class DungeonsHelperDatabase : RoomDatabase() {

    abstract fun weaponDao(): WeaponDao
    abstract fun spellDao(): SpellDao
    abstract fun itemsDao(): ItemsDao
    abstract fun charClassDao(): CharClassDao
    abstract fun raceDao(): RaceDao
    abstract fun characterDao(): CharacterDao
    abstract fun characterWithDetails(): CharacterWithDetailsDao
    abstract fun baseStatsDao(): BaseStatsDao
    abstract fun characterHpDao(): CharacterHpDao
    abstract fun characterProficiencyDao(): CharacterProficiencyDao
    abstract fun characterClassDao(): CharacterClassDao
    abstract fun traitDao(): TraitDao

    companion object {
        @Volatile
        private var INSTANCE: DungeonsHelperDatabase? = null

        fun getDatabase(context: Context): DungeonsHelperDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DungeonsHelperDatabase::class.java,
                    "Dungeons_Helper_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
