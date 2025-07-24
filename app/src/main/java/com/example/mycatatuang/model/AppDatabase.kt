package com.example.mycatatuang.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.example.mycatatuang.model.TransaksiDao

import androidx.room.RoomDatabase

@Database(entities = [Transaksi::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transaksiDao(): TransaksiDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mycatatuang.db"
                ).fallbackToDestructiveMigration() .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
