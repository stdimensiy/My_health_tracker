package ru.vdv.myhealthtracker.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.vdv.myhealthtracker.ui.common.ApplicableForMineList

/**
 * Класс Record (Запись - как запись в книге учета)
 * отвечает за хранение информации о кровяном давлении и сердечном ритме на момент измерения
 * @param id уникальный идентификатор записи (присваивается автоматически FireStore)
 * @param timestamp время записи измерения
 * @param systolicPressure Верхнее значение артериального давления (Систолическое)
 * @param diastolicPressure НИжнее значение давления (Диастолическое)
 * @param heartRate сердечный ритм (пульс, ударов в минуту)
 */
@Parcelize
data class Record(
    var id: String,
    val timestamp: String,
    val systolicPressure: Int,
    val diastolicPressure: Int,
    val heartRate: Int
) : Parcelable, ApplicableForMineList
