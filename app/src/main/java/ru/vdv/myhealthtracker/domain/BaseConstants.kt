package ru.vdv.myhealthtracker.domain

/**
 * основные константы:
 * @property NORMAL_SYSTOLIC_PRESSURE   среднестатистическое нормальное верхнее давление
 * @property NORMAL_DIASTOLIC_PRESSURE  среднестатистическое нормальное нижнее давление
 * @property HEADER_DATA_FORMAT         паттерн представления даты заголовка (разделителя)
 * @property MY_TAG                     ключ для отладки, можно будет удалит потом все по связям
 * @property FIRESTORE_COLLECTION_PATH ключ (наименование) коллекции
 */
object BaseConstants {
    const val MY_TAG = "Моя проверка"
    const val RETROFIT_FAILURE = "ОШИБКА при работе компонены RETROFIT"
    const val ROOM_FAILURE = "ОШИБКА при работе компонены ROOM"
    const val FIREBASE_FAILURE = "ОШИБКА при работе компонены FIREBASE"
    const val FIRESTORE_COLLECTION_PATH = "records"
    const val NORMAL_DIASTOLIC_PRESSURE = 120
    const val NORMAL_SYSTOLIC_PRESSURE = 80
    const val HEADER_DATA_FORMAT = "dd MMMM"
}