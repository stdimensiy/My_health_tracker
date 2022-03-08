package ru.vdv.myhealthtracker.ui.common

/**
 * Тип представления: (сквозная нумерация всех представлений для RV)
 * @property UNKNOWN_TYPE              неизвестный тип (при правильной работе программы не должен быть реализован)
 * @property RECORD                    запись (информационная записо состояния здоровья)
 * @property SEPARATOR                 разделитель (в принципе любой, пока только по дате)
 */
object BaseViewType {
    const val UNKNOWN_TYPE = 0
    const val RECORD = 1
    const val SEPARATOR = 2
}