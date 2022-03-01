package ru.vdv.myhealthtracker.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.vdv.myhealthtracker.domain.BaseViewType
import ru.vdv.myhealthtracker.domain.Record
import ru.vdv.myhealthtracker.domain.Separator
import ru.vdv.myhealthtracker.ui.common.ApplicableForMineList
import ru.vdv.myhealthtracker.ui.common.UnknownTypeViewHolder

class MainAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var items: List<ApplicableForMineList> = listOf()

    override fun getItemViewType(position: Int) = when (items[position]) {
        is Record -> BaseViewType.RECORD
        is Separator -> BaseViewType.SEPARATOR
        else -> BaseViewType.UNKNOWN_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            BaseViewType.SEPARATOR -> MainDataSeparatorViewHolder(layoutInflater, parent)
            BaseViewType.RECORD -> MainDataViewHolder(layoutInflater, parent)
            else -> UnknownTypeViewHolder(layoutInflater, parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (val item = items[position]) {
            is Record -> {
                holder as MainDataViewHolder
                holder.time.text = item.timestamp.substring(11, 16)
                holder.pressure.text = "${item.diastolicPressure} / ${item.diastolicPressure}"
                holder.heartRate.text = item.heartRate.toString()
            }
            is Separator -> {
                holder as MainDataSeparatorViewHolder
                holder.title.text = item.title
            }
            else -> {
                holder as UnknownTypeViewHolder
                holder.title.text = item.toString()
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}