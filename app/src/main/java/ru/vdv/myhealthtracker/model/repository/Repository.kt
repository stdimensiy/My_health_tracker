package ru.vdv.myhealthtracker.model.repository

import android.util.Log
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ru.vdv.myhealthtracker.domain.BaseConstants
import ru.vdv.myhealthtracker.domain.CallBack
import ru.vdv.myhealthtracker.domain.FSRecordStructure
import ru.vdv.myhealthtracker.domain.Record
import java.sql.Timestamp
import java.util.*
import kotlin.collections.ArrayList

class Repository : IRepository {

    private val db = Firebase.firestore

    override fun getList(callBack: CallBack<List<Record>>) {
        val tmpList: ArrayList<Record> = arrayListOf()
        db.collection(FSRecordStructure.COLLECTION_PATH)
            .orderBy(FSRecordStructure.Field.TIMESTAMP, Query.Direction.DESCENDING).get()
            .addOnSuccessListener {
                it?.let {
                    it.forEach { doc ->
                        val newRecord = Record(
                            doc.id,
                            doc.getDate(FSRecordStructure.Field.TIMESTAMP),
                            doc.getLong(FSRecordStructure.Field.SYSTOLIC_PRESSURE)?.toInt() ?: 0,
                            doc.getLong(FSRecordStructure.Field.DIASTOLIC_PRESSURE)?.toInt() ?: 0,
                            doc.getLong(FSRecordStructure.Field.HEART_RATE)?.toInt() ?: 0,
                        )
                        tmpList.add(newRecord)
                    }
                    callBack.onResult(tmpList.toList())
                }
            }
            .addOnFailureListener { exception ->
                Log.d(
                    "${BaseConstants.MY_TAG} / ${this.javaClass.simpleName}",
                    BaseConstants.FIREBASE_FAILURE + exception
                )
            }
    }

    override fun addNewRecord(
        record: Record,
        callBack: CallBack<Record>
    ) {
        val ts = Timestamp(Calendar.getInstance().timeInMillis)
        val docData = hashMapOf(
            FSRecordStructure.Field.TIMESTAMP to ts,
            FSRecordStructure.Field.DIASTOLIC_PRESSURE to record.diastolicPressure,
            FSRecordStructure.Field.SYSTOLIC_PRESSURE to record.systolicPressure,
            FSRecordStructure.Field.HEART_RATE to record.heartRate
        )
        db.collection(FSRecordStructure.COLLECTION_PATH).add(docData)
            .addOnSuccessListener { it ->
                record.id = it.id
                record.timestamp = Date(ts.time)
                callBack.onResult(record)
            }
            .addOnFailureListener { it ->
                Log.d(
                    "${BaseConstants.MY_TAG} / ${this.javaClass.simpleName}",
                    BaseConstants.FIREBASE_FAILURE + it
                )
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
        db.collection(FSRecordStructure.COLLECTION_PATH).document(record.id).delete()
            .addOnSuccessListener {
                callBack.onResult(record)
            }
    }
}