package ru.vdv.myhealthtracker.model.repository

import ru.vdv.myhealthtracker.domain.CallBack
import ru.vdv.myhealthtracker.ui.common.ApplicableForMineList

interface IRepository {
    fun getTestList(callBack: CallBack<List<ApplicableForMineList>>)
    fun getList(callBack: CallBack<List<ApplicableForMineList>>)
}