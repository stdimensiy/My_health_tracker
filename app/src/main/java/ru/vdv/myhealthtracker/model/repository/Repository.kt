package ru.vdv.myhealthtracker.model.repository

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ru.vdv.myhealthtracker.domain.BaseConstants
import ru.vdv.myhealthtracker.domain.CallBack
import ru.vdv.myhealthtracker.domain.Record
import ru.vdv.myhealthtracker.domain.Separator
import ru.vdv.myhealthtracker.ui.common.ApplicableForMineList
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Repository : IRepository {

    @SuppressLint("SimpleDateFormat")
    private val format = SimpleDateFormat(BaseConstants.HEADER_DATA_FORMAT)
    private val db = Firebase.firestore

    val testPlug = listOf<ApplicableForMineList>(
        Separator("25 октября"),
        Record("test_id_1", "2021-10-25 23:54:00", 137, 71, 59),
        Record("test_id_2", "2021-10-25 08:01:00", 126, 67, 49),
        Separator("24 октября"),
        Record("test_id_3", "2021-10-24 22:07:00", 141, 64, 63),
        Record("test_id_4", "2021-10-24 06:39:00", 127, 73, 58),
        Separator("23 октября"),
        Record("test_id_5", "2021-10-23 22:06:00", 150, 83, 55),
        Record("test_id_6", "2021-10-23 07:51:00", 129, 79, 58),
        Separator("22 октября"),
        Record("test_id_7", "2021-10-22 23:05:00", 150, 83, 55),
        Record("test_id_8", "2021-10-22 07:45:00", 130, 85, 58),
        Separator("21 октября"),
        Record("test_id_9", "2021-10-21 23:05:00", 150, 83, 55),
        Record("test_id_10", "2021-10-21 07:45:00", 130, 85, 58),
        Separator("20 октября"),
        Record("test_id_11", "2021-10-20 23:05:00", 150, 83, 55),
        Record("test_id_12", "2021-10-20 07:45:00", 130, 85, 58),
        Separator("19 октября"),
        Record("test_id_13", "2021-10-19 23:05:00", 150, 83, 55),
        Record("test_id_14", "2021-10-19 07:45:00", 130, 85, 58),

        )

    override fun getTestList(callBack: CallBack<List<ApplicableForMineList>>) {
        android.os.Handler().postDelayed({ callBack.onResult(testPlug) }, 2000)
    }

    override fun getList(callBack: CallBack<ArrayList<ApplicableForMineList>>) {
        var currentDataRecord: String = ""
        Log.d("Моя проверка", "Начинаю отработку запроса")
        val prepareList: ArrayList<ApplicableForMineList> = arrayListOf()
//        var currentDataRecord: String = ""
        db.collection("records").orderBy("timestamp",Query.Direction.DESCENDING).get()
            .addOnSuccessListener { documents ->
                if (documents != null) {
                    Log.d("Моя проверка", "Документ существует и получен ответ data: ${documents}")
                    for (document in documents) {
                        Log.d("Моя проверка", "${document.id} => ${document.data}")
                        if (currentDataRecord.equals(format.format(document.getDate("timestamp")))) {
                            prepareList.add(
                                Record(
                                    document.id,
                                    document.getDate("timestamp").toString(),
                                    document.getLong("diastolicPressure")?.toInt() ?: 0,
                                    document.getLong("systolicPressure")?.toInt() ?: 0,
                                    document.getLong("heartRate")?.toInt() ?: 0,
                                )
                            )
                        } else {
                            currentDataRecord = format.format(document.getDate("timestamp"))
                            prepareList.add(Separator(currentDataRecord))

                            val fullDataStamp = document.getDate("timestamp")
                            val diastolicPressure = document.getLong("systolicPressure")
                            prepareList.add(
                                Record(
                                    document.id,
                                    document.getDate("timestamp").toString(),
                                    document.getLong("diastolicPressure")?.toInt() ?: 0,
                                    document.getLong("systolicPressure")?.toInt() ?: 0,
                                    document.getLong("heartRate")?.toInt() ?: 0,
                                )
                            )
                        }
                        Log.d("Моя проверка", "текущая дата: $currentDataRecord")

                    }
                    android.os.Handler().postDelayed({ callBack.onResult(prepareList) }, 1000)

                } else {
                    Log.d("Моя проверка", "Пустой ответ")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Моя проверка", "ОШИБКА!!! ", exception)
            }
    }

    override fun addNewRecord(
        record: Record,
        callBack: CallBack<ArrayList<ApplicableForMineList>>
    ) {
        Log.d("Моя проверка", "Начинаю отработку запроса добавленияновой записи в базу")
        val docData = hashMapOf(
            "timestamp" to Timestamp(Calendar.getInstance().timeInMillis),
            "diastolicPressure" to record.diastolicPressure,
            "systolicPressure" to record.systolicPressure,
            "heartRate" to record.heartRate
        )
        db.collection("records").add(docData)
            .addOnSuccessListener { it ->
                Log.d("Моя проверка", "DocumentSnapshot added with ID: ${it.id}")
                record.id = it.id
                callBack.onResult(
                    arrayListOf(
                        record
                    )
                )
            }
            .addOnFailureListener { it ->
                Log.d("Моя проверка", "Error adding document", it)
            }

    }

    override fun updateRecord(record: Record, callBack: CallBack<Any>) {
        TODO("Not yet implemented")
    }

    override fun deleteAllRecord(callBack: CallBack<Any>) {
        TODO("Not yet implemented")
    }

    override fun deleteRecordById(record: Record, callBack: CallBack<Any>) {
        TODO("Not yet implemented")
    }


}