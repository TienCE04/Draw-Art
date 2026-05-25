package com.leansoft.draw.drawart.presentation.ui.background

import android.os.Bundle
import androidx.fragment.app.setFragmentResult
import com.leansoft.draw.drawart.base.BaseFragment
import com.leansoft.draw.drawart.databinding.FragmentBackgroundBinding
import com.leansoft.draw.drawart.presentation.viewmodel.NothingViewModel
import com.leansoft.draw.drawart.utils.ext.safeOnClickListener
import com.leansoft.draw.drawart.utils.ext.visible
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

@AndroidEntryPoint
class FragmentBackground : BaseFragment<FragmentBackgroundBinding, BgViewModel>() {
    private var adapter: BackgroundAdapter? = null
    private var bgSelected: Int? = null

    override fun getClassVM(): Class<BgViewModel> {
        return BgViewModel::class.java
    }

    override fun initView() {
        adapter = BackgroundAdapter { item ->
            binding.layoutHeader.btnApply.visible()
            bgSelected = item
        }

        binding.layoutHeader.btnApply.safeOnClickListener {
            bgSelected?.let {
                val bundle = Bundle().apply {
                    putInt("background", it)
                }
                setFragmentResult("bg_result", bundle)
            }
            navVM.back()
        }
        observe()
    }

    private fun observe() {
        adapter?.submitList(mViewModel.backgroundList)
    }

    override fun initViewBinding(): FragmentBackgroundBinding {
        return FragmentBackgroundBinding.inflate(layoutInflater)
    }
}