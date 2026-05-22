package com.leansoft.draw.drawart.presentation.ui.draw

import com.leansoft.draw.drawart.base.BaseFragment
import com.leansoft.draw.drawart.databinding.FragmentDrawFrameBinding
import com.leansoft.draw.drawart.utils.ext.safeOnClickListener

class FragmentDrawFrame : BaseFragment<FragmentDrawFrameBinding, FragmentDrawFrameVM>() {
    private var adapter: FragmentFrameSmallAdapter? = null

    override fun getClassVM(): Class<FragmentDrawFrameVM> {
        return FragmentDrawFrameVM::class.java
    }

    override fun initView() {
        register()
        observe()
    }

    private fun observe() {
        mainVM.itemAnimSelected.observe(viewLifecycleOwner) { data ->
            if (data == null) return@observe
            adapter?.submitList(data.listFrame)
        }
    }

    private fun register() {
        with(binding) {
            btnPlay.safeOnClickListener {

            }
        }
    }

    override fun initViewBinding(): FragmentDrawFrameBinding {
        return FragmentDrawFrameBinding.inflate(layoutInflater)
    }
}