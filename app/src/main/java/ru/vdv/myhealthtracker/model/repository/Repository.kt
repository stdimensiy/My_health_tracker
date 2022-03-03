package ru.vdv.myhealthtracker.model.repository

import ru.vdv.myhealthtracker.domain.CallBack
import ru.vdv.myhealthtracker.domain.Record
import ru.vdv.myhealthtracker.domain.Separator
import ru.vdv.myhealthtracker.ui.common.ApplicableForMineList
import java.sql.Timestamp

class Repository : IRepository {
    val testPlug = listOf<ApplicableForMineList>(
        Separator("25 октября"),
        Record("2021-10-25 23:54:00", 137, 71, 59),
        Record("2021-10-25 08:01:00", 126, 67, 49),
        Separator("24 октября"),
        Record("2021-10-24 22:07:00", 141, 64, 63),
        Record("2021-10-24 06:39:00", 127, 73, 58),
        Separator("23 октября"),
        Record("2021-10-23 22:06:00", 150, 83, 55),
        Record("2021-10-23 07:51:00", 129, 79, 58),
        Separator("22 октября"),
        Record("2021-10-22 23:05:00", 150, 83, 55),
        Record("2021-10-22 07:45:00", 130, 85, 58),
        Separator("21 октября"),
        Record("2021-10-21 23:05:00", 150, 83, 55),
        Record("2021-10-21 07:45:00", 130, 85, 58),
        Separator("20 октября"),
        Record("2021-10-20 23:05:00", 150, 83, 55),
        Record("2021-10-20 07:45:00", 130, 85, 58),
        Separator("19 октября"),
        Record("2021-10-19 23:05:00", 150, 83, 55),
        Record("2021-10-19 07:45:00", 130, 85, 58),

        )

    override fun getTestList(callBack: CallBack<List<ApplicableForMineList>>) {
        android.os.Handler().postDelayed({ callBack.onResult(testPlug) }, 2000)
    }
}