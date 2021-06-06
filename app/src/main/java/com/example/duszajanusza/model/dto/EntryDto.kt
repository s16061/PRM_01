package com.example.duszajanusza.model.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.duszajanusza.model.Entries
import java.time.LocalDate

@Entity(tableName = "entries")
data class EntryDto (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var money: Double,
    var shop: String,
    var date: String,
    var category: String
) {
    fun toModel() = Entries(
        id, money, shop, date, category,
    )
}