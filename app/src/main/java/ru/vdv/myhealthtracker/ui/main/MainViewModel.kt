package ru.vdv.myhealthtracker.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.vdv.myhealthtracker.domain.CallBack
import ru.vdv.myhealthtracker.domain.Record
import ru.vdv.myhealthtracker.ui.common.ApplicableForMineList
import ru.vdv.myhealthtracker.ui.common.BaseViewModel

class MainViewModel : BaseViewModel() {
    private val mListForMain = MutableLiveData<ArrayList<ApplicableForMineList>>().apply {
        value = arrayListOf()
    }

    val listForMain: LiveData<ArrayList<ApplicableForMineList>> = mListForMain

    fun fetchCurrentList() {
        Log.d(TAG, "Отправляю запрос в репозиторий")
        repository.getList(object : CallBack<ArrayList<ApplicableForMineList>> {
            override fun onResult(value: ArrayList<ApplicableForMineList>) {
                mListForMain.value = value
            }
        })
    }

    fun addNewRecord(systolicPressure: String, diastolicPressure: String, heartRate: String){
        Log.d(TAG, "Отправляю запрос в репозиторий на запись нового элемента")
        val newRecord: Record = Record("ttt", "2021-10-25 23:54:00", diastolicPressure.toInt(), systolicPressure.toInt(), heartRate.toInt())
        repository.addNewRecord(newRecord, object : CallBack<ArrayList<ApplicableForMineList>> {
            override fun onResult(value: ArrayList<ApplicableForMineList>) {
                Log.d(TAG, "Данные получил, меняю")
                val newData = mListForMain.value
                newData?.addAll(1, value)
                mListForMain.postValue(newData)
            }
        })
    }

    fun deleteRecord(record: Record){
        Log.d(TAG, "Пытаюсь удалить элемент из базы")
        repository.deleteRecordById(record, object : CallBack<Any> {
            override fun onResult(value: Any) {
                value as Record
                Log.d(TAG, "Данные получил, пытаюсь удалить из списка")
                val newData = mListForMain.value
                newData?.remove(value)
                mListForMain.postValue(newData)
            }
        })
    }
}