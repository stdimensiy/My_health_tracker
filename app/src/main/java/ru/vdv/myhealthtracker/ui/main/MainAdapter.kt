package ru.vdv.myhealthtracker.ui.main

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.vdv.myhealthtracker.domain.Record

class MainAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var items: List<Record> = listOf()

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return items.size
    }

}