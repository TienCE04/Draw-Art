package com.leansoft.draw.drawart.presentation.ui.background

import com.leansoft.draw.drawart.base.BaseFragment
import com.leansoft.draw.drawart.databinding.FragmentBackgroundBinding
import com.leansoft.draw.drawart.presentation.viewmodel.NothingViewModel

class FragmentBackground : BaseFragment<FragmentBackgroundBinding, NothingViewModel>() {
    var bgSelected = -1
    private var
    override fun getClassVM(): Class<NothingViewModel> {
        return NothingViewModel::class.java
    }

    override fun initView() {
        TODO("Not yet implemented")
    }

    override fun initViewBinding(): FragmentBackgroundBinding {
        return FragmentBackgroundBinding.inflate(layoutInflater)
    }
}