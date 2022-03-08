package ru.vdv.myhealthtracker.domain

/**
 * Структура полей хранения данных объектов коллекции FireStore коллекция :
 * @property COLLECTION_PATH            строка наименования коллекции
 */
object FSRecordStructure {
    const val COLLECTION_PATH = "records"

    /**
     * Перечень полей и типов данных объектов коллекции records:
     * @property TIMESTAMP                  Временная метка записи
     * @property SYSTOLIC_PRESSURE          Верхнее значение артериального давления (Систолическое)
     * @property DIASTOLIC_PRESSURE         Нижнее значение давления (Диастолическое)
     * @property HEART_RATE                 сердечный ритм (пульс, ударов в минуту)
     */
    object Field{
        const val TIMESTAMP = "timestamp"
        const val DIASTOLIC_PRESSURE = "diastolicPressure"
        const val SYSTOLIC_PRESSURE = "systolicPressure"
        const val HEART_RATE = "heartRate"
    }
}