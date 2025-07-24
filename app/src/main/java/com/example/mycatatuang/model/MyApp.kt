package com.example.mycatatuang.model

import android.app.Application
import androidx.room.Room

class MyApp : Application() {
    lateinit var db: AppDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "mycatatuang.db"
        ).allowMainThreadQueries() // sementara boleh di main thread
            .build()
    }
}
