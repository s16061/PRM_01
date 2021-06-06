package com.example.duszajanusza.model


import androidx.room.ColumnInfo
import androidx.room.Entity



data class Entries(
    var id: Int,
    var money: Double,
    var shop: String,
    var date: String,
    var category: String
)