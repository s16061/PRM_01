package com.example.duszajanusza.adapter

import android.app.AlertDialog
import android.content.Intent
import android.os.Looper
import android.view.LayoutInflater
import com.example.duszajanusza.model.Entries
import android.view.ViewGroup
import androidx.core.os.HandlerCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.duszajanusza.Utils
import com.example.duszajanusza.activites.AddEntryActivity
import com.example.duszajanusza.database.AppDatabase
import com.example.duszajanusza.databinding.ActivityMainBinding
import com.example.duszajanusza.databinding.ItemEntryBinding
import kotlin.concurrent.thread

class EntryAdapter(
    public val db: AppDatabase,
) : RecyclerView.Adapter<EntryVh>() {
    private val mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())
    private var entries = emptyList<Entries>()
    var costs = 0.0



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryVh {
        val binding = ItemEntryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return EntryVh(binding)

    }


    override fun onBindViewHolder(holder: EntryVh, position: Int) {
        val entry = (entries[position])
        holder.bind(entry)
        var context = holder.itemView.context
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Czy chcesz usunac dany wpis?")
        passValues(holder, entry)


        holder.itemView.setOnLongClickListener {
            builder.setPositiveButton("Tak") { dialog, which ->
                db.entries.deleteById(entry.id)
                load()
            }
            builder.setNegativeButton("Nie") { dialog, which ->
                dialog.dismiss()
            }
            builder.create()
            builder.show()
            true
        }

    }

    private fun passValues(holder: EntryVh, entry: Entries) {
        holder.itemView.setOnClickListener {
            var context = holder.itemView.context

            val intent = Intent(context, AddEntryActivity::class.java)


            intent.putExtra("id", entry.id)
            intent.putExtra("money", entry.money)
            intent.putExtra("date", entry.date)
            intent.putExtra("shop", entry.shop)
            intent.putExtra("category", entry.category)



            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = entries.size

    fun load() = thread {
        val newEntries = db.entries.getAll().map { it.toModel() }
        reloadView(newEntries)
    }




    private fun reloadView(newEntries: List<Entries>) {
        val diff = EntriesDiff(entries, newEntries)
        val result = DiffUtil.calculateDiff(diff)
        entries = newEntries
        mainHandler.post {
            notifyDataSetChanged()
            result.dispatchUpdatesTo(this)
        }
    }
}