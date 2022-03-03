package ru.vdv.myhealthtracker.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.vdv.myhealthtracker.databinding.ItemListDataSeparatorBinding

class MainDataSeparatorViewHolder(
    li: LayoutInflater,
    parent: ViewGroup,
    binding: ItemListDataSeparatorBinding = ItemListDataSeparatorBinding.inflate(li, parent, false)
) : RecyclerView.ViewHolder(binding.root) {
    var title = binding.tvDate
}