package ru.vdv.myhealthtracker.ui.common

/**
 * Класс Separator (Разделитель)
 * Фактически отвечает за хранение некоторого значения разделителя списка
 * от пустой строки до любой строки(как в данном случае) содержащей наименование месяца и дату
 * @param title Содержимое строки разделителя (заголовка)
 */
data class Separator(
    val title: String
) : ApplicableForMineList
