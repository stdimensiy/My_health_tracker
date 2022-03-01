package ru.vdv.myhealthtracker.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.vdv.myhealthtracker.ui.common.ApplicableForMineList
import java.sql.Timestamp

@Parcelize
data class Record(
    val timestamp: String,
    val diastolicPressure: Int,
    val systolicPressure: Int,
    val heartRate: Int
) : Parcelable, ApplicableForMineList
