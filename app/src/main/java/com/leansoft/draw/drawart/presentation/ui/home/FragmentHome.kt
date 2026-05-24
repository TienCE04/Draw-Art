package com.leansoft.draw.drawart.presentation.ui.home

import com.leansoft.draw.drawart.base.BaseFragment
import com.leansoft.draw.drawart.databinding.FragmentHomeBinding
import com.leansoft.draw.drawart.presentation.viewmodel.NothingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentHome : BaseFragment<FragmentHomeBinding, NothingViewModel>() {
    private var adapter: FragmentVPAdapter? = null

    private var adapterTab: TabCateAdapter? = null

    override fun getClassVM(): Class<NothingViewModel> {
        return NothingViewModel::class.java
    }

    override fun initView() {
        adapter = FragmentVPAdapter(this)
        adapterTab = TabCateAdapter()
        with(binding) {
            vpCate.adapter = adapter
            rcvTab.adapter = adapterTab

        }
        observe()
    }

    private fun observe() {
        mainVM.categories.observe(viewLifecycleOwner) {
            adapterTab?.submitList(it)
            adapter?.submitList(it)
        }
    }

    override fun initViewBinding(): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }
}