package com.example.duszajanusza

import com.example.duszajanusza.model.Entries
import java.util.ArrayList

class Utils {
    fun getEntriesForMonth(entries: List<Entries>, month: Int, year: Int): List<Entries> {
        if (month > 12 || month <= 0) {
            throw Error("Month should be between 1-12")
        }
        val filteredResults = ArrayList<Entries>()

        for (entry in entries) {
            val date = entry.date
            val parsedDate = date.split('/')
            val m = parsedDate[1].toInt()
            val y = parsedDate[2].toInt()
            if (m == month && y == year) {
                filteredResults.add(entry)
            }
        }

        return filteredResults
    }

    fun getEntriesSortedByDay(entries: List<Entries>): List<Entries> {
        return entries.sortedBy { it.date.split('/')[0] }
    }
}