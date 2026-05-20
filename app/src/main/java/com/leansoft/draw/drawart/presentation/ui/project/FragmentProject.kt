package com.leansoft.draw.drawart.presentation.ui.project

import com.leansoft.draw.drawart.base.BaseFragment
import com.leansoft.draw.drawart.databinding.FragmentProjectBinding
import com.leansoft.draw.drawart.presentation.viewmodel.NothingViewModel

class FragmentProject: BaseFragment<FragmentProjectBinding, NothingViewModel>(){
    override fun getClassVM(): Class<NothingViewModel> {
        return NothingViewModel::class.java
    }

    override fun initView() {
        TODO("Not yet implemented")
    }

    override fun initViewBinding(): FragmentProjectBinding {
        return FragmentProjectBinding.inflate(layoutInflater)
    }
}