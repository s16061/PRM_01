package com.example.duszajanusza.database

import android.content.Context
import android.database.Cursor
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.example.duszajanusza.database.dao.EntriesDao
import com.example.duszajanusza.model.dto.EntryDto

private const val DATABASE_FILENAME = "entries"
@Database(entities = [EntryDto::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract val entries: EntriesDao

    companion object{
        fun open(context: Context) = Room.databaseBuilder(
            context, AppDatabase::class.java, DATABASE_FILENAME
        ).allowMainThreadQueries()
            .build()



    }


}