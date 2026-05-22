package com.leansoft.draw.drawart.presentation.ui.draw

import androidx.lifecycle.MutableLiveData
import com.leansoft.draw.drawart.base.BaseViewModel
import com.leansoft.draw.drawart.domain.model.FrameModel

class FragmentDrawFrameVM : BaseViewModel() {
    private var _listFrameModel = MutableLiveData<List<FrameModel>>()
    val listFrameModel = _listFrameModel

    fun setListFrameModel(list: List<FrameModel>) {
        _listFrameModel.value = list
    }
}