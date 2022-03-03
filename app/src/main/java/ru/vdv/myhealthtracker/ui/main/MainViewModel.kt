package ru.vdv.myhealthtracker.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.vdv.myhealthtracker.domain.CallBack
import ru.vdv.myhealthtracker.ui.common.ApplicableForMineList
import ru.vdv.myhealthtracker.ui.common.BaseViewModel

class MainViewModel : BaseViewModel() {
    private val mListForMain = MutableLiveData<List<ApplicableForMineList>>().apply {
        value = listOf()
    }

    val listForMain: LiveData<List<ApplicableForMineList>> = mListForMain

    fun fetchCurrentList() {
        Log.d(TAG, "Отправляю запрос в репозиторий")
        repository.getList(object : CallBack<List<ApplicableForMineList>> {
            override fun onResult(value: List<ApplicableForMineList>) {
                mListForMain.value = value
            }
        })
    }
}