package com.leansoft.draw.drawart.presentation.ui.background

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.leansoft.draw.drawart.R
import com.leansoft.draw.drawart.base.BaseViewModel
import com.leansoft.draw.drawart.domain.model.BackgroundModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class BgViewModel @Inject constructor(
    @ApplicationContext val context: Context
) : BaseViewModel() {

    private var _pfsSelected = MutableLiveData<Int>()
    val pfsSelected: LiveData<Int> get() = _pfsSelected

    val backgroundList = listOf(
        BackgroundModel(
            context.getString(R.string.msg_paper),
            listOf(
                R.drawable.paper1,
                R.drawable.paper2,
                R.drawable.paper3,
                R.drawable.paper4,
                R.drawable.paper5,
                R.drawable.paper6,
                R.drawable.paper7,
                R.drawable.paper8,
                R.drawable.paper9,
            )
        ),
        BackgroundModel(
            context.getString(R.string.msg_pattern),
            listOf(
                R.drawable.pattern1,
                R.drawable.pattern2,
                R.drawable.pattern3,
                R.drawable.pattern4,
                R.drawable.pattern5,
                R.drawable.pattern6,
                R.drawable.pattern7,
                R.drawable.pattern8,
                R.drawable.pattern9,
            )
        ),
        BackgroundModel(
            context.getString(R.string.msg_scenes),
            listOf(
                R.drawable.scenes1,
                R.drawable.scenes2,
                R.drawable.scenes3,
                R.drawable.scenes4,
                R.drawable.scenes5,
                R.drawable.scenes6,
                R.drawable.scenes7,
                R.drawable.scenes8,
                R.drawable.scenes9,
            )
        )
    )

    fun setPfsSelected(fps: Int){
        _pfsSelected.value = fps
    }
}