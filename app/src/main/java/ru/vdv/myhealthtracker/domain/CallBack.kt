package ru.vdv.myhealthtracker.domain

interface CallBack<T> {
    fun onResult(value: T)
}