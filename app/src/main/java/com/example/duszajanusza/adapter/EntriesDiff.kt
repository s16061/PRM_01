package com.example.duszajanusza.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.duszajanusza.model.Entries

class EntriesDiff(
    val oldEntries: List<Entries>,
    val newEntries: List<Entries>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldEntries.size

    override fun getNewListSize(): Int = newEntries.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldEntries[oldItemPosition].id == newEntries[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldEntries[oldItemPosition].money == newEntries[newItemPosition].money &&
                oldEntries[oldItemPosition].shop == newEntries[newItemPosition].shop &&
                oldEntries[oldItemPosition].date == newEntries[newItemPosition].date &&
                oldEntries[oldItemPosition].category == newEntries[newItemPosition].category

}