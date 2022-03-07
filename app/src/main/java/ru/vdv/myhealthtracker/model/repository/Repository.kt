package ru.vdv.myhealthtracker.model.repository

import android.annotation.SuppressLint
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ru.vdv.myhealthtracker.domain.BaseConstants
import ru.vdv.myhealthtracker.domain.CallBack
import ru.vdv.myhealthtracker.domain.Record
import ru.vdv.myhealthtracker.domain.Separator
import ru.vdv.myhealthtracker.ui.common.ApplicableForMineList
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Repository : IRepository {

    @SuppressLint("SimpleDateFormat")
    private val format = SimpleDateFormat(BaseConstants.HEADER_DATA_FORMAT)
    private val db = Firebase.firestore

    override fun getList(callBack: CallBack<ArrayList<ApplicableForMineList>>) {
        var currentDataRecord: String = ""
        val prepareList: ArrayList<ApplicableForMineList> = arrayListOf()
        db.collection(BaseConstants.FIRESTORE_COLLECTION_PATH).orderBy("timestamp", Query.Direction.DESCENDING).get()
            .addOnSuccessListener { documents ->
                if (documents != null) {
                    for (document in documents) {
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
                    }
                    android.os.Handler().postDelayed({ callBack.onResult(prepareList) }, 1000)

                } else {

                }
            }
            .addOnFailureListener { exception ->

            }
    }

    override fun addNewRecord(
        record: Record,
        callBack: CallBack<ArrayList<ApplicableForMineList>>
    ) {
        val docData = hashMapOf(
            "timestamp" to Timestamp(Calendar.getInstance().timeInMillis),
            "diastolicPressure" to record.diastolicPressure,
            "systolicPressure" to record.systolicPressure,
            "heartRate" to record.heartRate
        )
        db.collection(BaseConstants.FIRESTORE_COLLECTION_PATH).add(docData)
            .addOnSuccessListener { it ->
                record.id = it.id
                callBack.onResult(
                    arrayListOf(
                        record
                    )
                )
            }
            .addOnFailureListener { it ->

            }

    }

    override fun updateRecord(record: Record, callBack: CallBack<Any>) {
        // в данной реализации и приложении  как таковом не предусматеривается возможность
        // корректировать ранее введенные данные (формально это приложение должно фиксировать
        // одномоментно показания и исключать возможность их корректировки)
        // метод оставлен как заглушка, дорабатывается и применяется только в режиме отладки
    }

    override fun deleteAllRecord(callBack: CallBack<Any>) {
        // могласно правил реализации базы данныех, удаление происходит поэлементно
        // следователбно в рамках работы репозттория данный метод оставлен не реализованным
        // реализация удаления всех элементов, с целью визуального контроля выполнения,
        // также докуентация firebase не рекомендует применение обфчного удаления для
        // больших коллекций, т.к, это негативнос казывается на производительности и безопасности,
        // для этиъ целей рекомендуется использование Cloud Function.
        // в случае принятия решения о реализации данного подхода следует вписать логику в этом методе.
    }

    override fun deleteRecord(record: Record, callBack: CallBack<Any>) {
        db.collection(BaseConstants.FIRESTORE_COLLECTION_PATH).document(record.id).delete()
            .addOnSuccessListener {
                callBack.onResult(
                    record
                )
            }
    }
}