package ru.vdv.myhealthtracker.ui.common

import androidx.lifecycle.ViewModel
import ru.vdv.myhealthtracker.domain.BaseConstants
import ru.vdv.myhealthtracker.model.repository.Repository

abstract class BaseViewModel : ViewModel() {
    protected val TAG = "${BaseConstants.MY_TAG} / ${this.javaClass.simpleName}"
    protected val repository = Repository()
}