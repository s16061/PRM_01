package com.example.duszajanusza.database.dao

import android.content.ContentValues
import android.database.Cursor
import androidx.room.*
import com.example.duszajanusza.model.dto.EntryDto

@Dao
interface EntriesDao {
    @Query("SELECT * FROM entries;")
    fun getAll(): List<EntryDto>

    @Query("SELECT SUM(money) FROM entries")
    fun getCosts(): Double

    @Query("SELECT * FROM entries WHERE id = :id")
    fun getById(id: Int): Cursor

    @Query("SELECT money FROM entries")
    fun getAllMoney() :Double

    @Query("SELECT date FROM entries")
    fun getAllDates() :String

    @Insert
    fun addEntry(entires: EntryDto)


    @Update
    fun updateEntry(entires: EntryDto)

    @Query("DELETE FROM entries WHERE id = :id;")
    fun deleteById(id: Int)
}

