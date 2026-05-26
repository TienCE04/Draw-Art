package com.leansoft.draw.drawart.presentation.ui.main

import androidx.navigation.fragment.findNavController
import com.leansoft.draw.drawart.R
import com.leansoft.draw.drawart.base.BaseFragment
import com.leansoft.draw.drawart.databinding.FragmentMainBinding
import com.leansoft.draw.drawart.presentation.viewmodel.NothingViewModel
import com.leansoft.draw.drawart.utils.ext.safeOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentMain : BaseFragment<FragmentMainBinding, NothingViewModel>() {
    private var adapter: FragmentTabMainAdapter? = null

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
        adapter = FragmentTabMainAdapter(this)

        binding.vpHome.apply {
            adapter = this@FragmentMain.adapter
            isUserInputEnabled = false
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
        with(binding) {
            layoutAdd.safeOnClickListener {
                findNavController().navigate(R.id.action_fragmentMain_to_create_graph)
            }
        }
    }

    private fun observer() {

    }

    override fun initViewBinding(): FragmentMainBinding {
        return FragmentMainBinding.inflate(layoutInflater)
    }
    override fun onDestroyView() {
        binding.vpHome.adapter = null
        adapter = null
        super.onDestroyView()
    }
}