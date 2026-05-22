package com.leansoft.draw.drawart.presentation.ui.main

import com.leansoft.draw.drawart.R
import com.leansoft.draw.drawart.base.BaseFragment
import com.leansoft.draw.drawart.databinding.FragmentMainBinding
import com.leansoft.draw.drawart.presentation.viewmodel.NothingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentMain : BaseFragment<FragmentMainBinding, NothingViewModel>() {
    private val adapter by lazy { FragmentTabMainAdapter(this) }

    override fun getClassVM(): Class<NothingViewModel> {
        return NothingViewModel::class.java
    }

    override fun initView() {
        mainVM.resetItemAnimSelected()
        initViewPager()
        register()
        observer()
        initBottomNavigation()
    }

    private fun initViewPager() {
        with(binding) {
            vpHome.adapter = adapter
            vpHome.isUserInputEnabled = false
        }
    }

    private fun initBottomNavigation() {
        binding.bottomNavMain.setOnItemSelectedListener { tab ->
            when (tab.itemId) {
                R.id.icHome -> {
                    binding.vpHome.currentItem = 0
                    true
                }

                R.id.icProject -> {
                    binding.vpHome.currentItem = 1
                    true
                }

                else -> false
            }
        }
    }

    private fun register() {

    }

    private fun observer() {

    }

    override fun initViewBinding(): FragmentMainBinding {
        return FragmentMainBinding.inflate(layoutInflater)
    }
}