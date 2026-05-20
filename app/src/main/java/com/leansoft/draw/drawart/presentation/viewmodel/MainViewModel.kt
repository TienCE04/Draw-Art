package com.leansoft.draw.drawart.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.leansoft.draw.drawart.base.BaseViewModel
import com.leansoft.draw.drawart.domain.model.CategoryGroupModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

) : BaseViewModel() {
    private var _categories = MutableLiveData<List<CategoryGroupModel>>()
    val categories = _categories

    init {

    }
}