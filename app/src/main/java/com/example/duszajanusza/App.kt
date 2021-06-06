package com.example.duszajanusza

import android.app.Application
import com.example.duszajanusza.database.AppDatabase

class App : Application() {
    val database by lazy { AppDatabase.open(this) }

    override fun onCreate() {
        super.onCreate()
        database

    }
}