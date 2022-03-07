package ru.vdv.myhealthtracker.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.vdv.myhealthtracker.R
import ru.vdv.myhealthtracker.domain.BaseConstants
import ru.vdv.myhealthtracker.domain.BaseViewType
import ru.vdv.myhealthtracker.domain.Record
import ru.vdv.myhealthtracker.domain.Separator
import ru.vdv.myhealthtracker.ui.common.ApplicableForMineList
import ru.vdv.myhealthtracker.ui.common.ILongClicked
import ru.vdv.myhealthtracker.ui.common.UnknownTypeViewHolder
import kotlin.math.abs
import kotlin.math.max

class MainAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var items: ArrayList<ApplicableForMineList> = arrayListOf()
    var itemLongClicked: ILongClicked? = null

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
                holder.systolicPressure.text = item.systolicPressure.toString()
                holder.diastolicPressure.text = item.diastolicPressure.toString()
                holder.heartRate.text = item.heartRate.toString()
                val d = max(
                    abs(item.diastolicPressure - BaseConstants.NORMAL_DIASTOLIC_PRESSURE),
                    abs(item.systolicPressure - BaseConstants.NORMAL_SYSTOLIC_PRESSURE)
                )
                when (d) {
                    in 0..9 -> holder.card.setBackgroundResource(R.drawable.bg_fine)
                    in 10..19 -> holder.card.setBackgroundResource(R.drawable.bg_slight_deviation_from_the_norm)
                    in 20..29 -> holder.card.setBackgroundResource(R.drawable.bg_average_deviation_from_the_norm)
                    in 30..39 -> holder.card.setBackgroundResource(R.drawable.bg_significant_deviation_from_the_norm)
                    in 40..1000 -> holder.card.setBackgroundResource(R.drawable.bg_critical_deviation_from_the_norm)
                }
                holder.card.setOnLongClickListener {
                    itemLongClicked?.onItemLongClicked(holder.itemView, position, item)
                    true
                }
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