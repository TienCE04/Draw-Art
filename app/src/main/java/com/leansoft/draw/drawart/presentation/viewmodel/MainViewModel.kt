package com.leansoft.draw.drawart.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leansoft.draw.drawart.base.BaseViewModel
import com.leansoft.draw.drawart.domain.model.AnimationModel
import com.leansoft.draw.drawart.domain.model.CategoryGroupModel
import com.leansoft.draw.drawart.domain.repository.RemoteDataRepository
import com.leansoft.draw.drawart.utils.Either
import com.leansoft.draw.drawart.utils.Failure
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: RemoteDataRepository
) : BaseViewModel() {
    private var _categories = MutableLiveData<List<CategoryGroupModel>>()
    val categories = _categories

    private var _itemAnimSelected = MutableLiveData<AnimationModel?>()
    val itemAnimSelected = _itemAnimSelected

    init {
        viewModelScope.launch {
            launch {
                repo.getListFrameTemp()
            }
            val data = repo.getCategoryData()
            data.fold(
                ::handleFailure,
                {
                    _categories.postValue(it)
                }
            )
        }
    }

    fun setItemAnimSelected(item: AnimationModel) {
        _itemAnimSelected.value = item
    }

    fun resetItemAnimSelected() {
        _itemAnimSelected.value = null
    }
}