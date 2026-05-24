package com.leansoft.draw.drawart.presentation.ui.draw

import androidx.lifecycle.lifecycleScope
import com.leansoft.draw.drawart.R
import com.leansoft.draw.drawart.base.BaseFragment
import com.leansoft.draw.drawart.databinding.FragmentDrawFrameBinding
import com.leansoft.draw.drawart.presentation.ui.custom_view.DrawCustomView
import com.leansoft.draw.drawart.utils.GlideUtils.loadBitmap
import com.leansoft.draw.drawart.utils.ext.safeOnClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentDrawFrame : BaseFragment<FragmentDrawFrameBinding, FragmentDrawFrameVM>() {
    private var adapter: FrameSmallAdapter? = null

    private var drawCustom: DrawCustomView? = null

    override fun getClassVM(): Class<FragmentDrawFrameVM> {
        return FragmentDrawFrameVM::class.java
    }

    override fun initView() {
        with(binding) {
            drawCustom = drawCustomView
        }
        viewLifecycleOwner.lifecycleScope.launch {
            val imgInputBitmap = loadBitmap(R.drawable.img_thumb_temp)
            imgInputBitmap?.let {
                drawCustom?.setup(imgInputBitmap)
            }
        }
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