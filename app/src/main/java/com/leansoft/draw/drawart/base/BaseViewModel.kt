package com.leansoft.draw.drawart.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.leansoft.draw.drawart.App
import com.leansoft.draw.drawart.presentation.viewmodel.MainViewModel
import com.leansoft.draw.drawart.presentation.viewmodel.NavigationViewModel
import com.leansoft.draw.drawart.utils.Failure

open class BaseViewModel : ViewModel() {
    private val _failure: MutableLiveData<Failure> = MutableLiveData()
    val failure: LiveData<Failure> = _failure
//    val networkUtils get() = App.instance.networkUtils

    fun handleFailure(failure: Failure) {
        _failure.postValue(failure)
    }
}