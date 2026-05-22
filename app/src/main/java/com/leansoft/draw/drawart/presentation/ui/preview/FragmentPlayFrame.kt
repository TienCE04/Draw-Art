package com.leansoft.draw.drawart.presentation.ui.preview

import com.leansoft.draw.drawart.base.BaseFragment
import com.leansoft.draw.drawart.databinding.FragmentPlayFrameBinding
import com.leansoft.draw.drawart.presentation.viewmodel.NothingViewModel

class FragmentPlayFrame : BaseFragment<FragmentPlayFrameBinding, NothingViewModel>() {
    override fun getClassVM(): Class<NothingViewModel> {
        return NothingViewModel::class.java
    }

    override fun initView() {
        register()
    }

    private fun register(){

    }

    override fun initViewBinding(): FragmentPlayFrameBinding {
        return FragmentPlayFrameBinding.inflate(layoutInflater)
    }
}