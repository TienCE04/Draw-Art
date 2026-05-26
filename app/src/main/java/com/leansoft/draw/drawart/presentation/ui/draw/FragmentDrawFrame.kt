package com.leansoft.draw.drawart.presentation.ui.draw

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.leansoft.draw.drawart.R
import com.leansoft.draw.drawart.base.BaseFragment
import com.leansoft.draw.drawart.databinding.FragmentDrawFrameBinding
import com.leansoft.draw.drawart.presentation.ui.custom_view.DrawCustomView
import com.leansoft.draw.drawart.utils.GlideUtils.loadBitmap
import com.leansoft.draw.drawart.utils.ext.gone
import com.leansoft.draw.drawart.utils.ext.safeOnClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentDrawFrame : BaseFragment<FragmentDrawFrameBinding, FragmentDrawFrameVM>() {
    private val args by navArgs<FragmentDrawFrameArgs>()
    private var adapter: FrameSmallAdapter? = null

    private var drawCustom: DrawCustomView? = null

    override fun getClassVM(): Class<FragmentDrawFrameVM> {
        return FragmentDrawFrameVM::class.java
    }

    override fun initView() {
        with(binding){
            layoutHeader.ivLogo.safeOnClickListener {
                findNavController().popBackStack()
            }
        }
        with(binding) {
            drawCustom = drawCustomView
            rcvListFrame.adapter=adapter
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