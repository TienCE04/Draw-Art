package com.leansoft.draw.drawart.presentation.ui.preview

import com.leansoft.draw.drawart.base.BaseFragment
import com.leansoft.draw.drawart.databinding.FragmentPreviewBinding
import com.leansoft.draw.drawart.presentation.viewmodel.NothingViewModel
import com.leansoft.draw.drawart.utils.ext.loadImage
import com.leansoft.draw.drawart.utils.ext.safeOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentPreview : BaseFragment<FragmentPreviewBinding, NothingViewModel>() {

    private val adapter by lazy { FragmentFramePreviewAdapter() }
    override fun getClassVM(): Class<NothingViewModel> {
        return NothingViewModel::class.java
    }

    override fun initView() {
        binding.rcvFrame.adapter = adapter
        register()
        observe()
    }

    private fun register(){
        with(binding){
            modeDraw.safeOnClickListener {

            }
            modeTemplate.safeOnClickListener {

            }
        }
    }
    private fun observe() {
        mainVM.itemAnimSelected.observe(viewLifecycleOwner) { data ->
            data?.let {
                with(binding) {
                    imgPreview.loadImage(it.urlGif)
                    tvNumberFrame.text = it.numberFrame.toString()
                    imgThumbUse.loadImage(it.thumbnail)
                    imgThumbDraw.loadImage(it.thumbnail, alpha = 0.4f)
                }
            }
        }
    }

    override fun initViewBinding(): FragmentPreviewBinding {
        return FragmentPreviewBinding.inflate(layoutInflater)
    }
}