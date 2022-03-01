package ru.vdv.myhealthtracker.domain

import ru.vdv.myhealthtracker.ui.common.ApplicableForMineList
import java.sql.Timestamp

data class Record(
    val timestamp: Timestamp,
    val diastolicPressure: Int,
    val systolicPressure: Int,
    val heartRate: Int
) : ApplicableForMineList
