package ru.vdv.myhealthtracker.model.repository

import ru.vdv.myhealthtracker.domain.CallBack
import ru.vdv.myhealthtracker.domain.Record
import ru.vdv.myhealthtracker.ui.common.ApplicableForMineList

interface IRepository {
    fun getTestList(callBack: CallBack<List<ApplicableForMineList>>)
    fun getList(callBack: CallBack<ArrayList<ApplicableForMineList>>)
    fun addNewRecord(record: Record, callBack: CallBack<ArrayList<ApplicableForMineList>>)
    fun updateRecord(record: Record, callBack: CallBack<Any>)
    fun deleteAllRecord(callBack: CallBack<Any>)
    fun deleteRecordById(record: Record, callBack: CallBack<Any>)
}