package com.example.duszajanusza.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.duszajanusza.databinding.ItemEntryBinding
import com.example.duszajanusza.model.Entries

class EntryVh(
    private val binding: ItemEntryBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(entry: Entries) = with(binding) {
        entryCost.text = entry.money.toString()
        entryLocation.text = entry.shop.toString()
        entryData.text = entry.date
        entryCategory.text = entry.category



    }





}
