package com.example.mobile.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Item::class], version = 1)
abstract class DungeonsHelperDatabase : RoomDatabase() {
    abstract fun itemsDao(): ItemsDao
    companion object {
        @Volatile
        private var INSTANCE: DungeonsHelperDatabase? = null
        fun getDatabase(context: Context): DungeonsHelperDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DungeonsHelperDatabase::class.java,
                    "Dungeons_Helper_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}